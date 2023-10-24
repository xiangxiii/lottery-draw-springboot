package lottery.draw.springboot.service.impl;

import lottery.draw.springboot.entity.Prize;
import lottery.draw.springboot.mapper.PrizeMapper;
import lottery.draw.springboot.service.IPrizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 奖品表 服务实现类
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@Service
public class PrizeServiceImpl extends ServiceImpl<PrizeMapper, Prize> implements IPrizeService {

}
