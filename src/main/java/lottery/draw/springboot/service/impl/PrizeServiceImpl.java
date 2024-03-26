package lottery.draw.springboot.service.impl;

import lottery.draw.springboot.entity.Prize;
import lottery.draw.springboot.mapper.PrizeMapper;
import lottery.draw.springboot.service.IPrizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


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
    public void test() throws InterruptedException {
        ExpiringMap<String, String> dataMap = ExpiringMap.builder()
                .maxSize(1000)
                .expiration(1000, TimeUnit.MILLISECONDS)
                .expirationPolicy(ExpirationPolicy.CREATED)
                .build();
        dataMap.put("1","1");
        dataMap.put("2","2");
        System.out.println(dataMap);

        Thread.sleep(900);
        dataMap.put("1","1",1000, TimeUnit.MILLISECONDS);
        dataMap.setExpiration("2",1000, TimeUnit.MILLISECONDS);
        System.out.println(dataMap);


        Thread.sleep(900);
        System.out.println(dataMap);


        dataMap.put("1","1");
        dataMap.put("2","2");
        System.out.println(dataMap);
    }
}
