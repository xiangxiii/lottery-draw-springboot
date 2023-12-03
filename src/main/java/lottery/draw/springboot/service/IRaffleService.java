package lottery.draw.springboot.service;

import lottery.draw.springboot.entity.Raffle;
import com.baomidou.mybatisplus.extension.service.IService;
import lottery.draw.springboot.vo.RaffleListVO;
import lottery.draw.springboot.vo.RaffleUserVO;
import lottery.draw.springboot.vo.RaffleVO;
import lottery.draw.springboot.vo.UserJoinVO;

import java.util.List;

/**
 * <p>
 * 抽奖活动表 服务类
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
public interface IRaffleService extends IService<Raffle> {

    /**
     * 新增抽奖活动
     * 有编辑状态 和 发起状态
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/9 15:31
     */
    void raffleAdd(RaffleVO raffleVO);

    /**
     * 修改抽奖活动
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/9 17:05
     */
    void raffleUpdate(RaffleVO raffleVO);

    /**
     * 抽奖活动列表
     * @param 
     * @throws
     * @return 
     * @author liux
     * @date 2023/11/10 13:35
     */
    List<RaffleListVO> raffleList(RaffleVO raffleVO);

    /**
     * 抽奖活动详情
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/10 14:38
     */
    RaffleVO raffleDetail(String raffleId);

    /**
     * 获取用户编辑抽奖信息
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/21 22:00
     */
    RaffleVO raffleExit(String userId);

    /**
     * 用户参加抽奖
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/26 6:50
     */
    void joinRaffle(UserJoinVO userJoinVO);

    /**
     * 开奖
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/26 6:50
     */
    void runRaffle(RaffleVO raffleVO);

    /**
     * 删除抽奖
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/30 20:05
     */
    void delete(String id);
    
    /**
     * 踢出用户
     * @param
     * @throws
     * @return 
     * @author liux
     * @date 2023/12/3 21:02
     */
    void outRaffle(UserJoinVO userJoinVO);

    List<RaffleUserVO> raffleUserList(String raffleId);
}
