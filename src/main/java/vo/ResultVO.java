package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作者:林国勇
 * 2019/7/15 14:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {

    private Integer code;
    private String msg;
    private Object data;
}
