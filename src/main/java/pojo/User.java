package pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 作者:林国勇
 * 2019/7/15 14:32
 */
@Data
public class User {
    private Long id;

    @NotBlank(message = "用户名为必填项,岂能不填")
    private String username;

    @NotBlank(message = "密码为必填项,岂能不填")
    private String password;

    @NotBlank(message = "手机号为必填项,岂能不填")
    private String phone;

    private Date created;
}
