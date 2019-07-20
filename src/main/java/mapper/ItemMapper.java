package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Item;

import java.util.List;

/**
 * 作者:林国勇
 * 2019/7/16 11:37
 */

public interface ItemMapper {

    //查询商品总条数
    Long findCountByName(@Param("name")String name);
    //分页查询商品具体信息
    List<Item> findItemByNameLikeAndLimit(@Param("name")String name,
                                          @Param("offset")Integer offset,
                                          @Param("size")Integer size);

    //3.添加商品
    Integer save(Item item);

    //4.根据ID删除商品
    Integer del(@Param("id") Long id);

    //5.修改商品
    Integer update(Item item);

    //根据ID查询商品
    Item findById(@Param("id")Long id);
}
