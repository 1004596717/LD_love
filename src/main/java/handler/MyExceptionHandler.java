package handler;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 作者:林国勇
 * 2019/7/18 15:07
 */
@Component
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String ex(Exception ex){
        ex.printStackTrace();
        return "error";
    }
}
