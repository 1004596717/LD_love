package interceptor;

import constant.SsmConstant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 作者:林国勇
 * 2019/7/18 11:19
 */

//实现权限校验
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获得session
        HttpSession session = request.getSession();

        //2.获取用户信息
        User user = (User) session.getAttribute(SsmConstant.USER);

        //3.判断是否为null
        if (user==null){
            //null为用户未登录
            response.sendRedirect(request.getContextPath()+"/user/login-ui");
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
