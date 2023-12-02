package lottery.draw.springboot.service.impl;

import lottery.draw.springboot.common.Result;
import lottery.draw.springboot.entity.Prize;
import lottery.draw.springboot.entity.Station;
import lottery.draw.springboot.mapper.PrizeMapper;
import lottery.draw.springboot.service.IPrizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

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

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> getmap() {
        String sql = "select * from tb_station limit 2";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }

    @Override
    public List<Station> getlist() {
        String sql = "select * from tb_station limit 2";
        List<Station> maps = jdbcTemplate.queryForList(sql, Station.class);
        return maps;
    }


}
