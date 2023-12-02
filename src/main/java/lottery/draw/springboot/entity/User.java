package lottery.draw.springboot.entity;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

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
      private String username;

      @ApiModelProperty("用户编码")
      private String code;

      @ApiModelProperty("手机号")
      private String phone;

      @ApiModelProperty("创建时间")
      @JSONField(format = DatePattern.NORM_DATETIME_PATTERN)
      private Date createTime;

      @ApiModelProperty("修改时间")
      @JSONField(format = DatePattern.NORM_DATETIME_PATTERN)
      private Date updateTime;

      @ApiModelProperty("余额")
      private String money;

      @ApiModelProperty("保留位")
      private String home;

      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("盐值")
      private String salt;

      @ApiModelProperty("角色")
      private String role;

      @ApiModelProperty("性别")
      private String sex;

      @ApiModelProperty("昵称")
      private String nickname;

      @ApiModelProperty("头像")
      private String avatar;
}
