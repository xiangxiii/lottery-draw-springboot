package lottery.draw.springboot.entity;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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

      @ApiModelProperty("抽奖人数")
      private Integer number;

      @ApiModelProperty("抽奖限定人数")
      private Integer maxNumber;

      @ApiModelProperty("创建时间")
      @JSONField(format = DatePattern.NORM_DATETIME_PATTERN)
      private Date createTime;

      @ApiModelProperty("修改时间")
      @JSONField(format = DatePattern.NORM_DATETIME_PATTERN)
      private Date updateTime;

      @ApiModelProperty("状态  编辑中1，进行中2，结束3")
      private String state;

      @ApiModelProperty("抽奖限定人数")
      private Integer click;

      @ApiModelProperty("查询码")
      private String query;
}
