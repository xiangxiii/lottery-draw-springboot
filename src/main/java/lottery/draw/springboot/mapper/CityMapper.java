package lottery.draw.springboot.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lottery.draw.springboot.entity.City;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName: CityMapper
 * @Description:
 * @Author:
 * @Date: 2023/2/14 14:28
 **/
@Mapper
public interface CityMapper extends BaseMapper<City> {

    List<City> queryCityByPid(Integer pid);

    void addCity();
}

