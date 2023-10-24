package lottery.draw.springboot.service.impl;

import lottery.draw.springboot.entity.Awards;
import lottery.draw.springboot.mapper.AwardsMapper;
import lottery.draw.springboot.service.IAwardsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
