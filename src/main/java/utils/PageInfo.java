package utils;

import lombok.Data;

import java.util.List;

/**
 * 作者:林国勇
 * 2019/7/16 11:33
 */
@Data
public class PageInfo<T> {
    //商品信息和分页信息

    //当前页
    private Integer page;
    //每页显示条数
    private Integer size;
    //查询总条数
    private Long total;
    //计算总页数
    private Integer pages;
    //计算起始索引
    private Integer offset;
    //商品信息
    private List<T> list;

    public PageInfo(Integer page, Integer size, Long total) {
        this.page = page <=0 ? 1 : page;
        this.size = size <=0 ? 5 : size;
        this.total = total <0 ? 0 :total;

        //计算pages和offset
        this.pages= (int)(this.total % this.size == 0 ? this.total / this.size : this.total / this.size + 1);
        this.offset=(this.page-1)*this.size;
    }
}
