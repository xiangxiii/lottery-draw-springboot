package lottery.draw.springboot.service;



import com.baomidou.mybatisplus.extension.service.IService;
import lottery.draw.springboot.entity.City;
import lottery.draw.springboot.vo.CityVO;

import java.util.List;

/**
 * @author liux
 * @date 2023/7/26 13:26
 */
public interface ICityService extends IService<City> {


    List<CityVO> cityList();
}
