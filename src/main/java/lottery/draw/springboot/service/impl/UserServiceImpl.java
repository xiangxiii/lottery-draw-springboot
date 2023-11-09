package lottery.draw.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lottery.draw.springboot.common.Constants;
import lottery.draw.springboot.dto.UserDTO;
import lottery.draw.springboot.entity.User;
import lottery.draw.springboot.exception.ServiceException;
import lottery.draw.springboot.mapper.UserMapper;
import lottery.draw.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lottery.draw.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);

            String token = TokenUtils.genToken(one.getId(), one.getPassword());
            userDTO.setToken(token);

            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", userDTO.getName());
        if (Objects.nonNull(getOne(queryWrapper))){
            throw new ServiceException(Constants.CODE_600, "用户名被占用");
        }

        User one = BeanUtil.copyProperties(userDTO, User.class);
        save(one);
        return one;
    }

    private User getUserInfo(UserDTO userDTO) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", userDTO.getName());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }
}
