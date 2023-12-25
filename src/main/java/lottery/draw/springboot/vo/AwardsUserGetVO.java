package lottery.draw.springboot.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户收货对象
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
    private LocalDateTime winTime;

    @ApiModelProperty("签收时间")
    private LocalDateTime getAwardsTime;

    @ApiModelProperty("是否签收   0待发货  1发货  -1签收")
    private String sign;

    @ApiModelProperty("抽奖活动id")
    private String raffleId;

    private String nickname;

    private String home;

    @ApiModelProperty("发起人")
    private String initiator;

    @ApiModelProperty("发起人昵称")
    private String initiatorNickname;

    @ApiModelProperty("举报")
    private String report;
}
