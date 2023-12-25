package lottery.draw.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lottery.draw.springboot.entity.City;
import lottery.draw.springboot.mapper.CityMapper;
import lottery.draw.springboot.service.ICityService;
import lottery.draw.springboot.vo.CityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liux
 * @date 2023/7/26 13:27
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {
    @Autowired
    private CityMapper cityMapper;

    @Override
    public List<CityVO> cityList(){
        List<City> cities = this.list();
        if (Objects.equals(cities.size(),0)){
            cityMapper.addCity();
            cities = this.list();
        }
        List<CityVO> city = cities.stream()
                .map(city1 -> BeanUtil.toBean(city1, CityVO.class))
                .sorted(Comparator.comparing(CityVO::getId))
                .collect(Collectors.toList());
        //展示列表
        List<CityVO> newCity = new ArrayList<>();

        for (CityVO cityVO : city){
                //省
                if (Objects.equals(cityVO.getType(),"1")){
                    newCity.add(cityVO);
                }
                for (CityVO vo : city){
                    if (Objects.equals(vo.getPid(),cityVO.getId())){
                        if (Objects.isNull(cityVO.getChildren())){
                            cityVO.setChildren(new ArrayList<>());
                        }
                        cityVO.getChildren().add(vo);
                    }
            }
        }
        return newCity;
    }

}
