package lottery.draw.springboot.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lottery.draw.springboot.entity.User;

import java.util.List;

/**
 * @author liux
 * @date 2023/11/9 15:29
 */
@Data
public class RaffleVO {
    private String id;

    private String userId;

    private String userName;

    private String type;

    private String state;

    private String datetime;

    private String number;

    private String maxNumber;

    private List<AwardsVO> awardsVOS;

    private String click;

    private String participationRate;

    //参加抽奖的用户id列表
    private List<String> ids;

    //参加抽奖的用户列表
    private List<User> userList;

    //查询条件，查询该用户参加了哪些抽奖
    private String joinUserId;

    //奖项以及分配情况
    private List<AwardsUserVO> awardsUserVOList;

    private String query;

    //查询条件
    private String sort;

    //1为倒序 0为顺序
    private int desc;
}
