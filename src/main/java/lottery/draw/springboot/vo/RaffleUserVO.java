package lottery.draw.springboot.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liux
 * @date 2023/11/23 21:47
 */
@Data
public class RaffleUserVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("参与者Id")
    private String userId;

    @ApiModelProperty("抽奖活动id")
    private String raffleId;

    @ApiModelProperty("编号")
    private Integer sort;

    @ApiModelProperty("实名")
    private String realName;

    private String username;

    private String nickname;
}
