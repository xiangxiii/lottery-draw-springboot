package lottery.draw.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 奖项表
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@Getter
@Setter
  @TableName("tb_awards")
@ApiModel(value = "Awards对象", description = "奖项表")
public class Awards implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        private String id;

      @ApiModelProperty("抽奖活动id")
      private String raffleId;

      @ApiModelProperty("奖品id")
      private String prizeId;

      @ApiModelProperty("奖项名称")
      private String name;

      @ApiModelProperty("奖项人数")
      private String number;


}
