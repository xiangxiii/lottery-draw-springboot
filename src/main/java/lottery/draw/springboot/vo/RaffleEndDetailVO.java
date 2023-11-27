package lottery.draw.springboot.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liux
 * @date 2023/11/27 20:06
 */
@Data
public class RaffleEndDetailVO {
    private String id;

    private String userId;

    private String userName;

    private String type;

    private String state;

    private String datetime;

    private String number;

    private String maxNumber;

    private List<AwardsUserVO> awardsUserVOList;
}
