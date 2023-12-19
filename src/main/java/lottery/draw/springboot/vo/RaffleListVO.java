package lottery.draw.springboot.vo;

import lombok.Data;


/**
 * @author liux
 * @date 2023/11/9 15:29
 */
@Data
public class RaffleListVO {
    private String id;

    private String userName;

    private String type;

    private String state;

    private String datetime;

    private String number;

    private String maxNumber;

    private AwardsVO awardsVO;

    private String nickname;

    private String url;

    private String click;

    private String participationRate;
}
