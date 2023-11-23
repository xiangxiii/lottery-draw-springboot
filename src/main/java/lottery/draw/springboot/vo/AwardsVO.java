package lottery.draw.springboot.vo;

import lombok.Data;

/**
 * @author liux
 * @date 2023/11/9 16:18
 */
@Data
public class AwardsVO {
    private String awardsName;

    private Integer number;

    private String prizeName;

    private Integer sort;
}
