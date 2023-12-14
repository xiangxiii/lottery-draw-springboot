package lottery.draw.springboot.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author liux
 * @date 2023/11/27 16:54
 */
@Data
public class AwardsUser {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("参与者Id")
    private String userId;

    @ApiModelProperty("抽奖活动id")
    private String awardId;

    @ApiModelProperty("中奖时间")
    private Date winTime;

    @ApiModelProperty("中奖时间")
    private LocalDateTime getAwardsTime;

    @ApiModelProperty("是否签收")
    private String sign;

    @ApiModelProperty("抽奖活动id")
    private String raffleId;

    @ApiModelProperty("收货地址")
    private String home;

    @ApiModelProperty("发奖者")
    private String initiator;

    @ApiModelProperty("举报")
    private String report;

}
