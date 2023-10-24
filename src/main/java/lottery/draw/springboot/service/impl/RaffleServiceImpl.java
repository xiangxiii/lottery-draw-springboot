package lottery.draw.springboot.service.impl;

import lottery.draw.springboot.entity.Raffle;
import lottery.draw.springboot.mapper.RaffleMapper;
import lottery.draw.springboot.service.IRaffleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 抽奖活动表 服务实现类
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@Service
public class RaffleServiceImpl extends ServiceImpl<RaffleMapper, Raffle> implements IRaffleService {

}
