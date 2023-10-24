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
 * 用户表
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@Getter
@Setter
  @TableName("tb_user")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("用户id")
        private String id;

      @ApiModelProperty("用户名")
      private String name;

      @ApiModelProperty("用户编码")
      private String code;

      @ApiModelProperty("手机号")
      private String phone;

      @ApiModelProperty("创建时间")
      private LocalDateTime createTime;

      @ApiModelProperty("修改时间")
      private LocalDateTime updateTime;

      @ApiModelProperty("余额")
      private String money;

      @ApiModelProperty("保留位")
      private String reserve;

      @ApiModelProperty("密码")
      private String password;


}
