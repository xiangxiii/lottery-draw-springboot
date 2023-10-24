package lottery.draw.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lottery.draw.springboot.common.Result;


import lottery.draw.springboot.service.IAwardsService;
import lottery.draw.springboot.entity.Awards;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 奖项表 前端控制器
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/awards")
public class AwardsController {
    @Resource
    private IAwardsService awardsService;


    @PostMapping
    public Result save(@RequestBody Awards awards) {
        awardsService.saveOrUpdate(awards);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        awardsService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        awardsService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(awardsService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(awardsService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Awards> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(awardsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}


