package lottery.draw.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 奖品表
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@Getter
@Setter
  @TableName("tb_prize")
@ApiModel(value = "Prize对象", description = "奖品表")
public class Prize implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("奖品ID")
        private String id;

      @ApiModelProperty("用户id")
      private String userId;

      @ApiModelProperty("奖品类型")
      private String type;

      @ApiModelProperty("奖品名称")
      private String name;

      @ApiModelProperty("奖品数")
      private String number;


}
