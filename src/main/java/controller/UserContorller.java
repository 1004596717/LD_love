package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pojo.User;
import service.UserService;
import utils.SendSMSUtil;
import vo.ResultVO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static constant.SsmConstant.*;

/**
 * 作者:林国勇
 * 2019/7/15 10:28
 */
//用户模块
@Controller
@RequestMapping("/user")
public class UserContorller {

    @Autowired
    private UserService userService;
    //发短信工具
    @Autowired
    private SendSMSUtil sendSMSUtil;

    //跳转到注册页面
    @GetMapping("/register-ui")
    public String registerUI(){
        //转发到注册页面
        return "user/register";
    }

    //1.JSON需要反序列化成一个pojo对象
    @PostMapping("/check-username")
    @ResponseBody  //直接响应,自动序列化成json
    public ResultVO checkUsername(@RequestBody User user){
        //调用service判断用户名
        Integer count= userService.checkUsername(user.getUsername());

       //使用ResultVO响应数据
        return new ResultVO(0, "成功", count);
       //封装响应数据的Map
    }


    @PostMapping(value = "/send-sms",produces = "text/html;charset=utf-8")
    @ResponseBody  //直接响应,自动序列化成json
    public String sendSMS(@RequestParam String phone, HttpSession session){
        //1.调用工具发短信
        String result = sendSMSUtil.sendSMS(session, phone);
        //2.将result响应给Ajax请求
        return result;

    }

    //执行注册
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, String registerCode, HttpSession session, RedirectAttributes redirectAttributes){
        //1.校验验证码
        if (!StringUtils.isEmpty(registerCode)){
            String code = (int) session.getAttribute(CODE)+"";
            if (!registerCode.equals(code)){
                //验证码不正确
                redirectAttributes.addAttribute("registerInfo","验证码错误!");
                return REDIRECT+REGISTER_UI;
            }
        }
        //2.校验参数是否真否合法
        if (bindingResult.hasErrors() || StringUtils.isEmpty(registerCode)){
            //参数不合法
            String msg = bindingResult.getFieldError().getDefaultMessage();
            if (StringUtils.isEmpty(msg)){
                msg="验证码为必填项,岂能不填";
            }
            redirectAttributes.addAttribute("registerInfo",msg);
            return REDIRECT+REGISTER_UI;
        }
        //3.校验service执行注册功能
        Integer count = userService.register(user);

        //4.根据service返回的结果跳转到指定页面
        if (count==1){
            //注册成功
            return REDIRECT+LOGIN_UI;
        }else {
            //注册失败
            redirectAttributes.addAttribute("registerInfo","服务器爆炸了!!!");
            return REDIRECT+REGISTER_UI;
        }
    }



    //跳转到登录页面
    @GetMapping("/login-ui")
    public String loginUI(){
        return "user/login";
    }


    //执行登录
    @PostMapping("/login")
    @ResponseBody
    public ResultVO login(String username,String password,HttpSession session){
        //1.校验参数是否合法
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            //参数不合法
            return new ResultVO(1, "用户名和密码为必填项,岂能不填", null);
        }
        //2.调用service执行登录
        User user=userService.login(username,password);
        //3.根据service返回结果判断登陆是否成功
        if (user!=null){
            //4.如果成功,将用户的信息放到session域中
            session.setAttribute(USER, user);
            return new ResultVO(0, "成功", null);
        }else {
            //5.如果失败,相应错误信息给Ajax请求
            return new ResultVO(2, "用户名或密码错误", null);
        }
    }


}
