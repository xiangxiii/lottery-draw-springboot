package lottery.draw.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lottery.draw.springboot.common.Result;


import lottery.draw.springboot.service.IRaffleService;
import lottery.draw.springboot.entity.Raffle;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 抽奖活动表 前端控制器
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/raffle")
public class RaffleController {
    @Resource
    private IRaffleService raffleService;


    @PostMapping
    public Result save(@RequestBody Raffle raffle) {
        raffleService.saveOrUpdate(raffle);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        raffleService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        raffleService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(raffleService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(raffleService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Raffle> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(raffleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}


