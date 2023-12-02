package lottery.draw.springboot.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lottery.draw.springboot.entity.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 用于展示抽奖活动中详细信息
 * @author liux
 * @date 2023/11/27 19:18
 */
@Data
public class AwardsUserGetVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("参与者Id")
    private String userId;

    @ApiModelProperty("抽奖奖项id")
    private String awardId;

    @ApiModelProperty("奖品名称")
    private String prizeName;

    @ApiModelProperty("中奖时间")
    private Date winTime;

    @ApiModelProperty("中奖时间")
    private LocalDateTime getAwardsTime;

    @ApiModelProperty("是否签收")
    private String sign;

    @ApiModelProperty("抽奖活动id")
    private String raffleId;

    private String userName;

    private String home;
}
