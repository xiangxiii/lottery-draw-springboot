package lottery.draw.springboot.service.impl;

import lottery.draw.springboot.entity.User;
import lottery.draw.springboot.mapper.UserMapper;
import lottery.draw.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
