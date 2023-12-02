package lottery.draw.springboot.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: TCity
 * @Description:地区表
 * @Author:
 * @Date: 2023/2/14 13:36
 **/
@Data
public class CityVO {
    @ApiModelProperty("主键")
    private int id;
    @ApiModelProperty("城市名称")
    private String name;
    @ApiModelProperty("城市父id")
    private int pid;
    @ApiModelProperty("城市类型 1.省2.市3.县")
    private String type;
    @ApiModelProperty("下级城市")
    private List<CityVO> children;
}

