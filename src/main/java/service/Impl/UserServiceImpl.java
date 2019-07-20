package service.Impl;

import mapper.UserMapper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pojo.User;
import service.UserService;

/**
 * 作者:林国勇
 * 2019/7/15 15:06
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer checkUsername(String username) {
        //健壮性代码
        if (!StringUtils.isEmpty(username)){
             username=username.trim();
        }

        Integer count = userMapper.findCountByUsername(username);
        return count;
    }

    @Override
    public Integer register(User user) {
        //1.对密码加密
        String nwp=new Md5Hash(user.getPassword(), null, 1024).toString();
        user.setPassword(nwp);
        //2.调用mapper保存数据
        Integer count = userMapper.save(user);
        //3.返回信息
        return count;
    }

    @Override
    public User login(String username, String password) {
        //1.根据用户名查询用户信息
        User user = userMapper.findByUserName(username);
        //2.判断查询的结果是否为null
        if (user!=null){
            //2.1如果不为null.判断密码
            String nwp=new Md5Hash(password, null, 1024).toString();

            //3.如果正确,直接返回查询到user
            if (user.getPassword().equals(nwp)){
                return user;
            }
        }
        //4.其他情况同意返回null
        return null;
    }
}
