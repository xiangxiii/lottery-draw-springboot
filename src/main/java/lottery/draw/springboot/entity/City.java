package lottery.draw.springboot.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;

/**
 * @ClassName: TCity
 * @Description:地区表
 * @Author:
 * @Date: 2023/2/14 13:36
 **/
@Data
@TableName("tb_city")
@ApiModel("流程实例节点日志")
public class City {
    /**
     * 主键
     */
    private int id;
    /**
     * 行政区划代码
     */
    @Getter(onMethod = @__( @JsonIgnore))
    private int code;
    /**
     * 名称
     */
    private String name;
    /**
     * 上级id
     */
    @Getter(onMethod = @__( @JsonIgnore))
    private int pid;
    /**
     * 类型:0=全部,1=省,2=市,3=地级市/区/县
     */
    @Getter(onMethod = @__( @JsonIgnore))
    private String type;

}

