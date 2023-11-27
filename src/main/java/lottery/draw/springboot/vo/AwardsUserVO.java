package lottery.draw.springboot.vo;

import lombok.Data;
import lottery.draw.springboot.entity.User;

import java.util.List;

/**
 * 用于展示抽奖活动中详细信息
 * @author liux
 * @date 2023/11/27 19:18
 */
@Data
public class AwardsUserVO {
    private String id;

    private String awardsName;

    private Integer number;

    private String prizeName;

    private Integer sort;

    private List<User> userList;
}
