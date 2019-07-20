package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.User;

/**
 * 作者:林国勇
 * 2019/7/15 10:55
 */
//映射User表
public interface UserMapper {
    //1.根据用户名查询条数
    Integer findCountByUsername(@Param("username")String username);

    //2.添加
    Integer save(User user);

    //3.根据用户名查询用户信息
    User findByUserName(@Param("username")String username);
}
