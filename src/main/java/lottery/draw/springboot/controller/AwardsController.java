package lottery.draw.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lottery.draw.springboot.vo.AwardsUserGetVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
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

    @PostMapping("/byUser")
    public Result getAwardsByUser(@RequestBody AwardsUserGetVO awardsUserGetVO) {
        return Result.success(awardsService.getAwardsByUser(awardsUserGetVO));
    }

    @PostMapping("/byAdmin")
    public Result getAllAwards(@RequestBody AwardsUserGetVO awardsUserGetVO) {
        return Result.success(awardsService.getAllAwards());
    }

    @GetMapping("/{raffleId}")
    public Result findOne(@PathVariable String raffleId) {
        return Result.success(awardsService.getAwardsByRaffle(raffleId));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Awards> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(awardsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @PostMapping("/updateMyAward")
    public Result updateMyAward(@RequestBody AwardsUserGetVO awardsUserGetVO) {
        awardsService.updateMyAward(awardsUserGetVO);
        return Result.success();
    }

}


