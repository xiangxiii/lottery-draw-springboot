package lottery.draw.springboot.mapper;

import lottery.draw.springboot.entity.Raffle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lottery.draw.springboot.entity.RaffleUser;

import java.util.List;

/**
 * <p>
 * 抽奖活动表 Mapper 接口
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
public interface RaffleMapper extends BaseMapper<Raffle> {
    /**
     * 用户参加抽奖
     * @param 
     * @throws
     * @return 
     * @author liux
     * @date 2023/11/23 21:32
     */
    void joinRaffle(RaffleUser raffleUser);

    /**
     * 根据抽奖id和用户id精确获取
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/27 15:43
     */
    RaffleUser getRaffleUser(String raffleId,String userId);

    void outRaffle(String raffleId,String userId);

    /**
     * 抽奖信息查询
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/27 15:50
     */
    List<RaffleUser> getListRaffleUser(String raffleId,String userId);

    RaffleUser getNewSort(String raffleId);
}
