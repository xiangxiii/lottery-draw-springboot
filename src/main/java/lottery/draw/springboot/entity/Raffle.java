package lottery.draw.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 抽奖活动表
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@Getter
@Setter
  @TableName("tb_raffle")
@ApiModel(value = "Raffle对象", description = "抽奖活动表")
public class Raffle implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("抽奖活动id")
        private String id;

      @ApiModelProperty("发起人id")
      private String userId;

      @ApiModelProperty("抽奖类型")
      private String type;

      @ApiModelProperty("开奖时间")
      private LocalDateTime time;

      @ApiModelProperty("抽奖限定人数")
      private String number;


}
