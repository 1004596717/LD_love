package service;

import mapper.Actest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pojo.Item;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者:林国勇
 * 2019/7/18 19:51
 */
public class ItemServiceTest extends Actest {

    @Autowired
    private ItemService itemService;
    @Test
    @Transactional
    public void update(){
        Item item=new Item();
        item.setId(4L);
        item.setName("中国华为");
        item.setPrice(new BigDecimal(45645));
        item.setProductionDate(new Date());
        item.setDescription("真他妈牛逼");
        item.setPic("hhhhhhhh");

        Integer update = itemService.update(item);
        System.out.println(update);
    }
}