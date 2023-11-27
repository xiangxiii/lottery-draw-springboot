package lottery.draw.springboot.mapper;

import lottery.draw.springboot.entity.Awards;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lottery.draw.springboot.entity.AwardsUser;

import java.util.List;

/**
 * <p>
 * 奖项表 Mapper 接口
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
public interface AwardsMapper extends BaseMapper<Awards> {
    void addAwardsUser(List<AwardsUser> awardsUser);

    List<AwardsUser> selectAwardUser(Awards award);
}
