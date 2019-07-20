package constant;



/**
 * 作者:林国勇
 * 2019/7/15 19:45
 */
public interface SsmConstant {

    //重定向
    String REDIRECT="redirect:";
    //放在session域中验证码的key
    String CODE="code";

    //跳转到注册页面的路径
    String REGISTER_UI="/user/register-ui";

    //跳转到登陆页面路径
    String LOGIN_UI = "/user/login-ui";

    //放在session语中的用户信息
    String USER="user";

    //跳转到添加页面
    String ITEM_ADD_UI="/item/add-ui";
}
