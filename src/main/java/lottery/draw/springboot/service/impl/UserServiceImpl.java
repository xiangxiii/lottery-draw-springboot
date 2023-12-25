package lottery.draw.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lottery.draw.springboot.common.Constants;
import lottery.draw.springboot.vo.UserVO;
import lottery.draw.springboot.entity.User;
import lottery.draw.springboot.enums.RoleEnum;
import lottery.draw.springboot.exception.ServiceException;
import lottery.draw.springboot.mapper.UserMapper;
import lottery.draw.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lottery.draw.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.*;

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

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO login(UserVO userVO) {
        User one = getUserInfo(userVO);
        if (one != null) {
            BeanUtil.copyProperties(one, userVO, true);

            String token = TokenUtils.genToken(one.getId(), one.getPassword());
            userVO.setToken(token);

            return userVO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserVO userVO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userVO.getUsername());
        if (Objects.nonNull(getOne(queryWrapper))){
            throw new ServiceException(Constants.CODE_600, "用户名被占用");
        }

        User one = BeanUtil.copyProperties(userVO, User.class);

        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(userVO.getPassword(), salt);
        one.setSalt(salt);
        one.setPassword(md5Password);

        one.setCreateTime(new Date());
        one.setRole(RoleEnum.USER.getCode());
        save(one);
        return one;
    }

    private User getUserInfo(UserVO userVO) {

        User user = userMapper.getSaltByName(userVO.getUsername());
        if(Objects.nonNull(user)){
            userVO.setSalt(user.getSalt());
            userVO.setPassword(getMd5Password(userVO.getPassword(),user.getSalt()));
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userVO.getUsername());
        queryWrapper.eq("password", userVO.getPassword());
        queryWrapper.eq("salt", userVO.getSalt());
        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    @Override
    public Map<String,User> getMapByUserIds(List<String> userIds) {
        if (CollectionUtils.isEmpty(userIds)){
            return new HashMap<>();
        }
        QueryWrapper<User> queryUser = new QueryWrapper<>();
        queryUser.in("id",userIds);
        List<User> users = this.list(queryUser);
        Map<String,User> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(),user);
        }
        return userMap;
    }

    @Override
    public List<UserVO> userList(UserVO userVO) {
        QueryWrapper<User> queryUser = new QueryWrapper<>();
        if (Objects.nonNull(userVO.getRole())){
            queryUser.eq("role", userVO.getRole());
        }
        List<User> list = this.list(queryUser);
        List<UserVO> result = new ArrayList<>();
        for (User user : list) {
            UserVO userVO1 = BeanUtil.copyProperties(user, UserVO.class);
            userVO1.setRole(RoleEnum.ofCode(userVO1.getRole()).getMessage());
            result.add(userVO1);
        }

        return result;
    }


    /**
     * 执行密码加密
     *
     * @param password 原始密码
     * @param salt     盐值
     * @return 加密后的密文
     */
    private String getMd5Password(String password, String salt) {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
