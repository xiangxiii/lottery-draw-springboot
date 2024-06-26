package lottery.draw.springboot.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lottery.draw.springboot.common.Constants;
import lottery.draw.springboot.entity.Code;
import lottery.draw.springboot.entity.WechatTokenEntity;
import lottery.draw.springboot.vo.RaffleVO;
import lottery.draw.springboot.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lottery.draw.springboot.common.Result;


import lottery.draw.springboot.service.IUserService;
import lottery.draw.springboot.entity.User;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserVO userVO){
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserVO dto = userService.login(userVO);
        return Result.success(dto);
    }
    @PostMapping("/register")//需改进
    public Result register(@RequestBody UserVO userVO){
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(userVO));
    }

    @PostMapping("/update")
    public Result save(@RequestBody UserVO user) {
        userService.updateById(BeanUtil.copyProperties(user, User.class));
        return Result.success("update");
    }

    @PostMapping("/list")
    public Result list(@RequestBody UserVO userVO) {
        return Result.success(userService.userList(userVO));
    }

    @PostMapping("/updateRole")
    public Result updateRole(@RequestBody UserVO userVO) {
        User byId = userService.getById(userVO.getId());
        byId.setRole(userVO.getRole());
        return Result.success(userService.updateById(byId));
    }

    @PostMapping("/wx")
    public Result wx(@RequestBody Code code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + "wx0a6d7386aa68f627"+
                "&secret=" + "696a4dbf7efd1bdadca0aea0df651fdb"+
                "&js_code=" + code.getCode() +
                "&grant_type=authorization_code";
        String s = HttpUtil.get(url);
        JSONObject jsonObject = JSON.parseObject(s);
        String openid = jsonObject.getString("openid");
        String session_key = jsonObject.getString("session_key");
        WechatTokenEntity wechatTokenEntity = new WechatTokenEntity();
        wechatTokenEntity.setOpenid(openid);
        wechatTokenEntity.setSession_key(session_key);
        return Result.success(wechatTokenEntity);
    }

    @PostMapping("/wxlogin")
    public Result wxlogin(@RequestBody Code code) {
        User byId = userService.getById(code.getOpenid());
        if (Objects.isNull(byId)){
            User user = new User();
            user.setId(code.getOpenid());
            user.setUsername(code.getOpenid());
            user.setNickname(code.getName());
            user.setAvatar(code.getImg());
            user.setRole("2");
            userService.save(user);
        }
        return Result.success(userService.getById(code.getOpenid()));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.removeById(id);
        return Result.success();
    }

    @GetMapping("/report")
    public Result report() {
        return Result.success(userService.getReport());
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        userService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}


