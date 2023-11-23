package lottery.draw.springboot.service;

import lottery.draw.springboot.vo.UserVO;
import lottery.draw.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
public interface IUserService extends IService<User> {
    UserVO login(UserVO userVO);

    User register(UserVO userVO);
}
