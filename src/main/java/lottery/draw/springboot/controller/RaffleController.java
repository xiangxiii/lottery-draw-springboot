package lottery.draw.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lottery.draw.springboot.vo.RaffleVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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
    public Result save(@RequestBody RaffleVO raffleVO) {
        if (Objects.isNull(raffleVO.getId())){
            raffleService.raffleAdd(raffleVO);
        }else{
            raffleService.raffleUpdate(raffleVO);
        }

        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody RaffleVO raffleVO) {
        return Result.success();
    }

    @PostMapping("/list")
    public Result list(@RequestBody RaffleVO raffleVO) {
        return Result.success(raffleService.raffleList(raffleVO));
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable String id) {
        return Result.success(raffleService.raffleDetail(id));
    }

    @GetMapping("/getExit/{userId}")
    public Result getExit(@PathVariable String userId) {
        return Result.success(raffleService.raffleExit(userId));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        raffleService.removeById(id);
        return Result.success();
    }


}


