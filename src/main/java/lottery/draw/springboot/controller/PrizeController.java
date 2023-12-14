package lottery.draw.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lottery.draw.springboot.common.Result;


import lottery.draw.springboot.service.IPrizeService;
import lottery.draw.springboot.entity.Prize;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 奖品表 前端控制器
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/prize")
public class PrizeController {
    @Resource
    private IPrizeService prizeService;



    @PostMapping
    public Result save(@RequestBody String s) {

        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        prizeService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        prizeService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(prizeService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(prizeService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Prize> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(prizeService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}


