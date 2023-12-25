package lottery.draw.springboot.controller;


import lottery.draw.springboot.vo.RaffleVO;
import lottery.draw.springboot.vo.UserJoinVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Objects;
import lottery.draw.springboot.common.Result;


import lottery.draw.springboot.service.IRaffleService;
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
            return Result.success(raffleService.raffleAdd(raffleVO));
        }else{
            return Result.success(raffleService.raffleUpdate(raffleVO));
        }
    }

    @PostMapping("/list")
    public Result list(@RequestBody RaffleVO raffleVO) {
        return Result.success(raffleService.raffleList(raffleVO));
    }

    @PostMapping("/getByQuery")
    public Result getByQuery(@RequestBody String query) {
        return Result.success(raffleService.getByQuery(query));
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
    public Result delete(@PathVariable String id) {
        raffleService.delete(id);
        return Result.success();
    }

    @PostMapping("/join")
    public Result join(@RequestBody UserJoinVO userJoinVO) {
        raffleService.joinRaffle(userJoinVO);
        return Result.success();
    }

    @PostMapping("/runRaffle")
    public Result runRaffle(@RequestBody RaffleVO raffleVO) {
        raffleService.runRaffle(raffleVO);
        return Result.success();
    }

    @PostMapping("/raffleUserList")
    public Result raffleUserList(@RequestBody String raffleId) {
        return Result.success(raffleService.raffleUserList(raffleId));
    }

    @PostMapping("/outRaffle")
    public Result outRaffle(@RequestBody UserJoinVO userJoinVO) {
        raffleService.outRaffle(userJoinVO);
        return Result.success();
    }

}


