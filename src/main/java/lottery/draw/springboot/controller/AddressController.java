package lottery.draw.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
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
        addressService.saveOrUpdate(address);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
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
    public Result findOne(@PathVariable Integer id) {
        return Result.success(addressService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(addressService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}


