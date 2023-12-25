package lottery.draw.springboot.service;

import lottery.draw.springboot.vo.RaffleListVO;
import lottery.draw.springboot.vo.RaffleVO;
import lottery.draw.springboot.vo.UserVO;
import lottery.draw.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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

    Map<String,User> getMapByUserIds(List<String> userIds);

    List<UserVO> userList(UserVO userVO);
}
