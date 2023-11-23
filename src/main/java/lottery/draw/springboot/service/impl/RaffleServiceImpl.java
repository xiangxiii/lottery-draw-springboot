package lottery.draw.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lottery.draw.springboot.entity.Awards;
import lottery.draw.springboot.entity.Raffle;
import lottery.draw.springboot.entity.User;
import lottery.draw.springboot.enums.StateEnum;
import lottery.draw.springboot.mapper.RaffleMapper;
import lottery.draw.springboot.service.IRaffleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lottery.draw.springboot.vo.AwardsVO;
import lottery.draw.springboot.vo.RaffleListVO;
import lottery.draw.springboot.vo.RaffleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import javax.xml.crypto.Data;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 抽奖活动表 服务实现类
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@Service
public class RaffleServiceImpl extends ServiceImpl<RaffleMapper, Raffle> implements IRaffleService {

    @Autowired
    private RaffleServiceImpl raffleService;

    @Autowired
    private AwardsServiceImpl awardsService;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public void raffleAdd(RaffleVO raffleVO) {

        //保存抽奖活动信息
        Raffle raffle = BeanUtil.copyProperties(raffleVO,Raffle.class);
        Date now = new Date();
        raffle.setCreateTime(now);
        raffle.setNumber(0);
        save(raffle);

        QueryWrapper<Raffle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", raffle.getUserId());
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");
        raffle = getOne(queryWrapper);

        //保存奖项信息
        awardsService.saveAwardsList(raffleVO.getAwardsVOS(),raffle);
    }

    @Override
    public void raffleUpdate(RaffleVO raffleVO) {
        if (Objects.isNull(raffleVO.getId())){
            return;
        }

        //保存抽奖活动信息
        Raffle raffle = BeanUtil.copyProperties(raffleVO,Raffle.class);
        raffle.setUpdateTime(new Date());
        updateById(raffle);

        raffle = getById(raffleVO.getId());
        awardsService.awardsUpdate(raffleVO.getAwardsVOS(),raffle);

    }

    @Override
    public List<RaffleListVO> raffleList(RaffleVO raffleVO) {
        QueryWrapper<Raffle> queryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(raffleVO.getUserId())){
            queryWrapper.eq("user_id", raffleVO.getUserId());
        }
        if (Objects.nonNull(raffleVO.getState())){
            queryWrapper.eq("state", raffleVO.getState());
        }
        List<Raffle> raffles = this.list(queryWrapper);

        //根据用户id获取用户列表信息
        List<String> userIds = raffles.stream().map(Raffle::getUserId).distinct().collect(Collectors.toList());
        Map<String, User> userMap = userService.getMapByUserIds(userIds);
        //根据抽奖活动id获取活动第一项奖项
        List<String> raffleIds = raffles.stream().map(Raffle::getId).distinct().collect(Collectors.toList());
        Map<String, AwardsVO> raffleMap = awardsService.getMapByRaffle(raffleIds,"1");

        //整合数据
        List<RaffleListVO> raffleListVOS = new ArrayList<>();
        for (Raffle raffle : raffles) {
            RaffleListVO raffleListVO = BeanUtil.copyProperties(raffle,RaffleListVO.class);
            raffleListVO.setUserName(userMap.get(raffle.getUserId()).getUsername());
            raffleListVO.setAwardsVO(raffleMap.get(raffle.getId()));
            raffleListVO.setState(StateEnum.ofCode(raffleListVO.getState()).getMessage());
            raffleListVOS.add(raffleListVO);
        }

        return raffleListVOS;
    }

    @Override
    public RaffleVO raffleDetail(String raffleId) {
        Raffle raffle = this.getById(raffleId);
        RaffleVO raffleVO = BeanUtil.copyProperties(raffle,RaffleVO.class);
        List<Awards> awards = awardsService.getListByRaffleId(raffle.getId());
        List<AwardsVO> awardsVOS = new ArrayList<>();
        for (Awards award : awards) {
            AwardsVO awardsVO = BeanUtil.copyProperties(award,AwardsVO.class);
            awardsVOS.add(awardsVO);
        }
        raffleVO.setAwardsVOS(awardsVOS);
        raffleVO.setUserName(userService.getById(raffle.getUserId()).getUsername());

        return raffleVO;
    }

    @Override
    public RaffleVO raffleExit(String userId) {
        QueryWrapper<Raffle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("state", "1");
        List<Raffle> raffles = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(raffles)){
            RaffleVO raffleVO = new RaffleVO();
            raffleVO.setUserId(userId);
            raffleVO.setType("定时抽奖");
            raffleVO.setMaxNumber("100");
            raffleVO.setState(StateEnum.EXIT.getMessage());
            List<AwardsVO> awardsVOS = new ArrayList<>();
            AwardsVO awardsVO = new AwardsVO();
            awardsVO.setAwardsName("一等奖");
            awardsVO.setNumber(1);
            awardsVO.setPrizeName("奖品名称");
            awardsVO.setSort(1);
            awardsVOS.add(awardsVO);
            raffleVO.setAwardsVOS(awardsVOS);

            return raffleVO;
        }else {
            RaffleVO raffleVO = BeanUtil.copyProperties(raffles.get(0),RaffleVO.class);
            List<Awards> awards = awardsService.getListByRaffleId(raffles.get(0).getId());
            List<AwardsVO> awardsVOS = new ArrayList<>();
            for (Awards award : awards) {
                AwardsVO awardsVO = BeanUtil.copyProperties(award,AwardsVO.class);
                awardsVOS.add(awardsVO);
            }
            raffleVO.setAwardsVOS(awardsVOS);
            raffleVO.setState(StateEnum.ofCode(raffleVO.getState()).getMessage());
            raffleVO.setUserName(userService.getById(raffles.get(0).getUserId()).getUsername());
            return raffleVO;
        }
    }


}
