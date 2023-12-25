package lottery.draw.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lottery.draw.springboot.common.Result;


import lottery.draw.springboot.service.IAddressService;
import lottery.draw.springboot.entity.Address;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 收货地址表 前端控制器
 * </p>
 *
 * @author liuxiang
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Resource
    private IAddressService addressService;


    @PostMapping
    public Result save(@RequestBody Address address) {
        if (Objects.isNull(address.getId())){
            addressService.save(address);
        }else{
            addressService.updateById(address);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        addressService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        addressService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(addressService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable String id) {
        return Result.success(addressService.getById(id));
    }

    @PostMapping("/list")
    public Result list(@RequestBody Address address) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(address.getUserId())){
            queryWrapper.eq("user_id",address.getUserId());
        }
        queryWrapper.orderByDesc("id");
        return Result.success(addressService.list( queryWrapper));
    }
}


