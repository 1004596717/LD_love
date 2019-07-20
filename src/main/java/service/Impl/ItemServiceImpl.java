package service.Impl;

import mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Item;
import service.ItemService;
import utils.PageInfo;

import java.util.List;

/**
 * 作者:林国勇
 * 2019/7/16 11:36
 */
@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemMapper itemMapper;

    public PageInfo<Item> finItemAndLimit(String name,Integer page,Integer size){
        Long total = itemMapper.findCountByName(name);
        PageInfo<Item> pageInfo=new PageInfo<>(page, size, total);

        List<Item> list=itemMapper.findItemByNameLikeAndLimit(name, pageInfo.getOffset(), pageInfo.getSize());
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public Integer save(Item item) {


        return itemMapper.save(item);
    }

    @Override
    public Integer del(Long id) {

        return itemMapper.del(id);
    }

    @Override
    public Integer update(Item item) {
        return itemMapper.update(item);
    }

    @Override
    public Item findById(Long id) {
        return itemMapper.findById(id);
    }
}
