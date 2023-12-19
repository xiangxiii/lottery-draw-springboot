package lottery.draw.springboot.service.impl;

import lottery.draw.springboot.entity.Address;
import lottery.draw.springboot.mapper.AddressMapper;
import lottery.draw.springboot.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收货地址表 服务实现类
 * </p>
 *
 * @author liuxiang
 * @since 2023-12-19
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
