package service;

import pojo.User;

/**
 * 作者:林国勇
 * 2019/7/15 15:06
 */
public interface UserService {
    Integer checkUsername(String username);

    Integer register(User user);

    //执行登录
    User login(String username, String password);
}
