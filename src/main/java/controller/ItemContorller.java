package controller;

import mapper.ItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pojo.Item;
import service.ItemService;
import utils.PageInfo;
import vo.ResultVO;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static constant.SsmConstant.REDIRECT;

/**
 * 作者:林国勇
 * 2019/7/16 11:12
 */
@Controller
@RequestMapping("/item")
public class ItemContorller {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemMapper itemMapper;
    //跳转到商品展示页面
    //1.接受三个参数
    @GetMapping("/list")
    public String list(String name,
                       @RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "size",defaultValue = "5")Integer size,
                       Model model){

        //2.调用service查询
        PageInfo<Item> pageInfo=itemService.finItemAndLimit(name,page,size);

        //3.放到request域中
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("name",name);

        //4.转发页面
        return "item/item_list";
    }


    //跳转到添加页面
    @GetMapping("/add-ui")
    public String addUI(String addInfo,Model model){
        model.addAttribute("addInfo",addInfo);
        return "item/item_add";
    }


    @Value("${pic_type}")
    public String pictype;

    //添加商品信息
    @PostMapping("/add")
    public String add(@Valid Item item, BindingResult bindingResult, MultipartFile picFile, HttpServletRequest request) throws IOException {
        //============================校验参数===========================
        if (bindingResult.hasErrors()){
            //获得具体信息
            String message = bindingResult.getFieldError().getDefaultMessage();
            //参数不合法
            return null;
        }

//        =====================图片上传=====================
        //1.对图片的大小做校验,要求图片小于5m
        long size = picFile.getSize();
        if (size>5242880L){
            //图片过大
            return null;
        }

        boolean flag=false;
        //2.对图片的类型做校验
        String[] types = pictype.split(",");
        for (String type : types) {
            if (StringUtils.endsWithIgnoreCase(picFile.getOriginalFilename(), type)){
                //图片类型正确
                flag=true;
                break;
            }
        }
        if (!flag){
            //图片类型不正确
            return null;
        }
        //3.校验图片是否损坏
        BufferedImage image = ImageIO.read(picFile.getInputStream());
        if (image==null){
            //图片已经损坏
            return null;
        }

        //=====================将图片保存到本地========================
        //1.起名字
        String prefix = UUID.randomUUID().toString();
        String suffix = StringUtils.substringAfterLast(picFile.getOriginalFilename(), ".");
        String newName=prefix+"."+suffix;

        //2.将图片保存到本地
        String  path=request.getServletContext().getRealPath("")+"\\static\\img\\"+newName;
        File file=new File(path);
        picFile.transferTo(file);
        //3.封装图片的访问路径
        String pic="http://localhost:8080/Spring_Demo/static/img/"+newName;


        //===========================保存商品信息=================================
        item.setPic(pic);

        //调用service保存商品信息
        Integer count=itemService.save(item);
        //判断count
        if (count==1){
            return REDIRECT+"/item/list";
        }else {
            //添加商品失败
            return null;
        }
    }



    //根据ID删除商品信息
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public ResultVO delete(@PathVariable Long id){
        //1.调用service删除商品
        Integer count=itemService.del(id);

        //2.根据结果给页面响应商品
        if (count==1){
            //删除成功
            return new ResultVO(0, "成功", null);
        }else {
            //删除失败
            return new ResultVO(345, "删除商品失败", null);
        }
    }


    //跳转到修改页面
    @GetMapping("/update-ui/{id}")
    public String updateUI(@PathVariable Long id,Model model){
       Item item=itemService.findById(id);
       model.addAttribute("item",item);
        return "item/item_update";
    }

    @PostMapping("/update")
    public String update(@Valid Item item,BindingResult bindingResult){

        Integer count = itemService.update(item);
        System.out.println();
        //判断count
        if (count==1){
            return REDIRECT+"/item/item_list";
        }else {
            //添加商品失败
            return REDIRECT + "/item/update/"+item.getId();
        }

    }

}