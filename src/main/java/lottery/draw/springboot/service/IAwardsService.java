package lottery.draw.springboot.service;

import lottery.draw.springboot.entity.Awards;
import com.baomidou.mybatisplus.extension.service.IService;
import lottery.draw.springboot.entity.AwardsUser;
import lottery.draw.springboot.vo.AwardsUserGetVO;
import lottery.draw.springboot.vo.AwardsUserVO;

import java.util.List;


/**
 * <p>
 * 奖项表 服务类
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
public interface IAwardsService extends IService<Awards> {
    /**
     * 根据抽奖活动id查询奖项信息和中奖信息
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/27 20:00
     */
    List<AwardsUserVO> getAwardsByRaffle(String raffleId);

    /**
     * 获取当前用户的中奖情况
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/27 20:30
     */
    List<AwardsUserGetVO> getAwardsByUser(String raffleId);

    /**
     * 改变中奖信息
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/30 21:06
     */
    void updateMyAward(AwardsUserGetVO awardsUserGetVO);
}
