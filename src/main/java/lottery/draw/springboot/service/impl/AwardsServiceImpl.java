package lottery.draw.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lottery.draw.springboot.entity.Awards;
import lottery.draw.springboot.entity.Raffle;
import lottery.draw.springboot.mapper.AwardsMapper;
import lottery.draw.springboot.service.IAwardsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lottery.draw.springboot.vo.AwardsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 奖项表 服务实现类
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@Service
public class AwardsServiceImpl extends ServiceImpl<AwardsMapper, Awards> implements IAwardsService {

    @Autowired
    private AwardsMapper awardsMapper;

    /**
     * 更新奖项
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/9 17:18
     */
    public void awardsUpdate(List<AwardsVO> awardsVOList, Raffle raffle) {
        QueryWrapper<Awards> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("raffle_id", raffle.getId());
        remove(queryWrapper);

        saveAwardsList(awardsVOList, raffle);
    }

    /**
     * 保存奖项信息
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/10 9:42
     */
    public void saveAwardsList(List<AwardsVO> awardsVOList, Raffle raffle) {
        List<Awards> awardsList = new ArrayList<>();
        for (int i=0; i<awardsVOList.size(); i++){
            Awards awards = BeanUtil.copyProperties(awardsVOList.get(i),Awards.class);
            awards.setRaffleId(raffle.getId());
            awardsList.add(awards);
        }
        saveBatch(awardsList);
    }

    public Map<String, AwardsVO> getMapByRaffle(List<String> raffleIds,String sort) {
        Map<String, AwardsVO> awardsVOMap = new HashMap<>();
        QueryWrapper<Awards> queryAward = new QueryWrapper<>();
        queryAward.in("raffle_id",raffleIds);
        if (Objects.nonNull(sort)){
            queryAward.eq("sort",sort);
        }

        List<Awards> awardsList = this.list(queryAward);
        for (Awards awards : awardsList) {
            AwardsVO awardsVO = BeanUtil.copyProperties(awards,AwardsVO.class);
            awardsVOMap.put(awards.getRaffleId(),awardsVO);
        }
        return awardsVOMap;
    }

    public List<Awards> getListByRaffleId(String raffleId){
        QueryWrapper<Awards> queryAward = new QueryWrapper<>();
        queryAward.in("raffle_id",raffleId);
        return list(queryAward);
    }
}
