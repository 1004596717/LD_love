package mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pojo.Item;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者:林国勇
 * 2019/7/18 10:56
 */

public class ItemMapperTest extends Actest{

    @Autowired
    private ItemMapper itemMapper;

    @Test
    @Transactional
    public void save(){
        Item item=new Item();
        item.setName("xxxxxx");
        item.setPrice(new BigDecimal(1.1));
        item.setProductionDate(new Date());
        item.setDescription("yyyyyyyyyyyyy");
        item.setPic("zzzzzzzzzzzz");
        Integer save = itemMapper.save(item);
        System.out.println(save);
    }

    @Test
    @Transactional
    public void update(){
        Item item=new Item();
        item.setId(4l);
        item.setName("中国华为");
        item.setPrice(new BigDecimal(45645));
        item.setProductionDate(new Date());
        item.setDescription("真他妈牛逼");
        item.setPic("hhhhhhhh");
        System.out.println(item.toString());
        Integer count = itemMapper.update(item);
        System.out.println(count);
    }
}