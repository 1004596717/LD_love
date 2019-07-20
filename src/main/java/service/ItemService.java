package service;

import pojo.Item;
import utils.PageInfo;

/**
 * 作者:林国勇
 * 2019/7/16 11:36
 */
public interface ItemService {


    PageInfo<Item> finItemAndLimit(String name, Integer page, Integer size);

    Integer save(Item item);

    Integer del(Long id);

    Integer update(Item item);
    Item findById(Long id);
}
