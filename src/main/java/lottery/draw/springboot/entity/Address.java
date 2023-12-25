package lottery.draw.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 收货地址表
 * </p>
 *
 * @author liuxiang
 * @since 2023-12-19
 */
@Getter
@Setter
  @TableName("tb_address")
@ApiModel(value = "Address对象", description = "收货地址表")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id")
      private String id;

      @ApiModelProperty("收货地址")
      private String address;

      @ApiModelProperty("收货人")
      private String user;

      @ApiModelProperty("联系电话")
      private String phone;

      @ApiModelProperty("用户ID")
      private String userId;


}
