package lottery.draw.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lottery.draw.springboot.common.Constants;
import lottery.draw.springboot.entity.Awards;
import lottery.draw.springboot.entity.Raffle;
import lottery.draw.springboot.entity.RaffleUser;
import lottery.draw.springboot.entity.User;
import lottery.draw.springboot.enums.RaffleTypeEnum;
import lottery.draw.springboot.enums.StateEnum;
import lottery.draw.springboot.exception.ServiceException;
import lottery.draw.springboot.mapper.RaffleMapper;
import lottery.draw.springboot.service.IRaffleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lottery.draw.springboot.utils.UUIDUtil;
import lottery.draw.springboot.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.collections.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private RaffleMapper raffleMapper;

    DateTimeFormatter formatterDaily = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter formatterDailySS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM月dd日 HH:mm");
    @Override
    public RaffleVO raffleAdd(RaffleVO raffleVO) {

        //保存抽奖活动信息
        Raffle raffle = BeanUtil.copyProperties(raffleVO,Raffle.class);
        Date now = new Date();
        raffle.setCreateTime(now);
        raffle.setNumber(0);
        raffle.setClick(0);
        LocalDateTime time = LocalDateTime.parse(raffleVO.getDatetime(),formatterDaily);
        raffle.setTime(time);

        if (StringUtils.equals(raffle.getType(), RaffleTypeEnum.INTERNAL.getCode()) && StringUtils.equals(raffle.getState(),StateEnum.START.getCode()) && StringUtils.isBlank(raffle.getQuery())){
            raffle.setQuery(UUIDUtil.getUUID6());
        }

        save(raffle);

        QueryWrapper<Raffle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", raffle.getUserId());
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");
        raffle = getOne(queryWrapper);

        //保存奖项信息
        awardsService.saveAwardsList(raffleVO.getAwardsVOS(),raffle);

        return BeanUtil.copyProperties(raffle, RaffleVO.class);
    }

    @Override
    public RaffleVO raffleUpdate(RaffleVO raffleVO) {
        if (Objects.isNull(raffleVO.getId())){
            return null;
        }
        //保存抽奖活动信息
        Raffle raffle = BeanUtil.copyProperties(raffleVO,Raffle.class);
        raffle.setUpdateTime(new Date());
        if (raffleVO.getDatetime().length() == 16){
            raffleVO.setDatetime(raffleVO.getDatetime()+":00");
        }
        LocalDateTime time = LocalDateTime.parse(raffleVO.getDatetime(),formatterDaily);
        raffle.setTime(time);
        updateById(raffle);
        //
        if (StringUtils.equals(raffle.getType(), RaffleTypeEnum.INTERNAL.getCode()) && StringUtils.equals(raffle.getState(),StateEnum.START.getCode()) && StringUtils.isBlank(raffle.getQuery())){
            raffle.setQuery(UUIDUtil.getUUID6());
        }

        raffle = getById(raffleVO.getId());
        awardsService.awardsUpdate(raffleVO.getAwardsVOS(),raffle);

        return BeanUtil.copyProperties(raffle, RaffleVO.class);
    }

    @Override
    public List<RaffleListVO> raffleList(RaffleVO raffleVO) {
        this.timeRunRaffle();

        QueryWrapper<Raffle> queryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(raffleVO.getUserId())){
            queryWrapper.eq("user_id", raffleVO.getUserId());
        }
        if (Objects.nonNull(raffleVO.getState())){
            queryWrapper.eq("state", raffleVO.getState());
        }
        if (Objects.nonNull(raffleVO.getType())){
            queryWrapper.eq("type", raffleVO.getType());
        }
        if (Objects.nonNull(raffleVO.getQuery())){
            queryWrapper.eq("query", raffleVO.getQuery());
        }
        if(!StringUtils.equals(raffleVO.getType(),RaffleTypeEnum.INTERNAL.getCode()) && Objects.isNull(raffleVO.getUserId())){
            //假如不主动查询内部抽奖  隐藏内部抽奖
            queryWrapper.ne("type","3");
        }

        if (Objects.nonNull(raffleVO.getJoinUserId())){
            List<RaffleUser> listRaffleUser = raffleMapper.getListRaffleUser(null, raffleVO.getJoinUserId());
            List<String> ids = listRaffleUser.stream().map(RaffleUser::getRaffleId).collect(Collectors.toList());
            queryWrapper.in("id", ids);
        }
        if (StringUtils.isNotBlank(raffleVO.getSort())) {
            if(raffleVO.getDesc()==1){
                queryWrapper.orderByDesc(raffleVO.getSort());
            }
            if(raffleVO.getDesc()==0){
                queryWrapper.orderByAsc(raffleVO.getSort());
            }
        }
        queryWrapper.ne("state","1");
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
            raffleListVO.setDatetime(raffle.getTime().format(formatter));
            raffleListVO.setNickname(userMap.get(raffle.getUserId()).getNickname());
            raffleListVO.setUrl(userMap.get(raffle.getUserId()).getAvatar());
            double v = Double.parseDouble(raffleListVO.getNumber()) * 100 / Double.parseDouble(raffleListVO.getClick());
            if (v>=0 && v<=100){
                raffleListVO.setParticipationRate((int)v+"%");
            }
            raffleListVOS.add(raffleListVO);
        }

        return raffleListVOS;
    }

    @Override
    public RaffleVO raffleDetail(String raffleId) {
        //计数
        this.addClick(raffleId);
        Raffle raffle = this.getById(raffleId);
        RaffleVO raffleVO = BeanUtil.copyProperties(raffle,RaffleVO.class);
        //添加奖品信息
        List<Awards> awards = awardsService.getListByRaffleId(raffle.getId());
        List<AwardsVO> awardsVOS = new ArrayList<>();
        for (Awards award : awards) {
            AwardsVO awardsVO = BeanUtil.copyProperties(award,AwardsVO.class);
            awardsVOS.add(awardsVO);
        }
        raffleVO.setAwardsVOS(awardsVOS);

        raffleVO.setUserName(userService.getById(raffle.getUserId()).getUsername());
        raffleVO.setDatetime(raffle.getTime().format(formatterDaily));
        double v = Double.parseDouble(raffleVO.getNumber()) * 100 / Double.parseDouble(raffleVO.getClick());
        if (v>=0 && v<=100){
            raffleVO.setParticipationRate((int)v+"%");
        }else{
            raffleVO.setParticipationRate("0%");
        }

        List<RaffleUser> listRaffleUser = raffleMapper.getListRaffleUser(raffleId, null);
        List<String> collect = listRaffleUser.stream().map(RaffleUser::getUserId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(collect)){
            List<User> users = userService.listByIds(collect);
            raffleVO.setUserList(users);
        }

        if (StringUtils.equals(raffle.getState(),"3")||StringUtils.equals(raffle.getState(),"4")){
            raffleVO.setAwardsUserVOList(awardsService.getAwardsByRaffle(raffleId));
        }
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
            raffleVO.setMaxNumber("100");
            raffleVO.setState(StateEnum.EXIT.getMessage());
            List<AwardsVO> awardsVOS = new ArrayList<>();
            AwardsVO awardsVO = new AwardsVO();
            awardsVO.setAwardsName("一等奖");
            awardsVO.setNumber(1);
            awardsVO.setPrizeName("奖品名称");
            awardsVO.setAvatar("http://tmp/pjbtgjidAR4N03792ac3c38359e52ce086160183f87c.png");
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
            raffleVO.setDatetime(raffles.get(0).getTime().format(formatterDailySS));
            return raffleVO;
        }
    }

    @Override
    public RaffleVO getByQuery(String query) {
        QueryWrapper<Raffle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("query", query);
        queryWrapper.orderByDesc("time");
        queryWrapper.eq("state","2");
        List<Raffle> list = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)){
            return BeanUtil.copyProperties(list.get(0),RaffleVO.class);
        }
        return null;
    }

    @Override
    public void joinRaffle(UserJoinVO joinVO) {
        if (Objects.isNull(raffleMapper.getRaffleUser(joinVO.getRaffleId(),joinVO.getUserId()))){
            Raffle raffle = raffleService.getById(joinVO.getRaffleId());
            if (raffle.getNumber()>=raffle.getMaxNumber()){
                throw new ServiceException(Constants.CODE_600, "抽奖人数达到上限");
            }
            raffle.setNumber(raffle.getNumber()+1);


            RaffleUser raffleUser = new RaffleUser();
            raffleUser.setRaffleId(joinVO.getRaffleId());
            raffleUser.setUserId(joinVO.getUserId());
            raffleUser.setRealName(joinVO.getRealName());
            RaffleUser ru = raffleMapper.getNewSort(raffle.getId());
            if(Objects.isNull(ru)){
                raffleUser.setSort(1);
            }else{
                raffleUser.setSort(ru.getSort());
            }
            raffleMapper.joinRaffle(raffleUser);
            raffleService.updateById(raffle);
        }else{
            throw new ServiceException(Constants.CODE_600, "你已参加过抽奖");
        }
    }

    @Override
    public void runRaffle(RaffleVO raffleVO) {
        Raffle raffle = raffleService.getById(raffleVO.getId());
        if (!StringUtils.equals(raffle.getState(),"2")){
            throw new ServiceException(Constants.CODE_600, "当前抽奖不能开奖");
        }
        //奖品分配
        awardsService.allocationAwards(raffle);
        raffle.setState("3");
        raffle.setUpdateTime(new Date());
        this.updateById(raffle);

    }

    @Override
    public void delete(String id) {
        if (CollectionUtils.isEmpty(raffleMapper.getListRaffleUser(id,null))){
            this.removeById(id);
        }else{
            throw new ServiceException(Constants.CODE_600, "有参与者不能删除");
        }
    }

    @Override
    public void outRaffle(UserJoinVO userJoinVO) {
        Raffle byId = raffleService.getById(userJoinVO.getRaffleId());
        byId.setNumber(byId.getNumber()-1);
        raffleService.updateById(byId);
        raffleMapper.outRaffle(userJoinVO.getRaffleId(),userJoinVO.getUserId());
    }

    @Override
    public List<RaffleUserVO> raffleUserList(String raffleId) {
        List<RaffleUser> listRaffleUser = raffleMapper.getListRaffleUser(raffleId, null);
        List<RaffleUserVO> list = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        for (RaffleUser raffleUser : listRaffleUser) {
            userIds.add(raffleUser.getUserId());
        }
        if (CollectionUtils.isEmpty(userIds)){
            return new ArrayList<>();
        }
        List<User> users = userService.listByIds(userIds);
        Map<String,User> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(),user);
        }
        for (RaffleUser raffleUser : listRaffleUser) {
            RaffleUserVO vo = BeanUtil.copyProperties(raffleUser,RaffleUserVO.class);
            vo.setNickname(userMap.get(vo.getUserId()).getNickname());
            vo.setUsername(userMap.get(vo.getUserId()).getUsername());
            list.add(vo);
        }
        return list;
    }

    public void timeRunRaffle(){
        QueryWrapper<Raffle> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("time",new Date());
        queryWrapper.eq("state", "2");
        List<Raffle> list = this.list(queryWrapper);
        for (Raffle raffle : list) {
            this.runRaffle(BeanUtil.copyProperties(raffle,RaffleVO.class));
        }
    }

    public void addClick(String id){
        Raffle byId = this.getById(id);
        byId.setClick(byId.getClick()+1);
        this.updateById(byId);
    }

}
