package lottery.draw.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lottery.draw.springboot.entity.*;
import lottery.draw.springboot.enums.SignEnum;
import lottery.draw.springboot.mapper.AwardsMapper;
import lottery.draw.springboot.mapper.RaffleMapper;
import lottery.draw.springboot.service.IAwardsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lottery.draw.springboot.service.IUserService;
import lottery.draw.springboot.vo.AwardsUserGetVO;
import lottery.draw.springboot.vo.AwardsUserVO;
import lottery.draw.springboot.vo.AwardsVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 奖项表 服务实现类
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
@Service
public class AwardsServiceImpl extends ServiceImpl<AwardsMapper, Awards> implements IAwardsService {

    @Autowired
    private AwardsMapper awardsMapper;

    @Autowired
    private AwardsServiceImpl awardsService;

    @Autowired
    private RaffleMapper raffleMapper;

    @Autowired
    private IUserService userService;
    /**
     * 更新奖项
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/9 17:18
     */
    public void awardsUpdate(List<AwardsVO> awardsVOList, Raffle raffle) {
        QueryWrapper<Awards> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("raffle_id", raffle.getId());
        remove(queryWrapper);

        saveAwardsList(awardsVOList, raffle);
    }

    /**
     * 保存奖项信息
     * @param
     * @throws
     * @return
     * @author liux
     * @date 2023/11/10 9:42
     */
    public void saveAwardsList(List<AwardsVO> awardsVOList, Raffle raffle) {
        List<Awards> awardsList = new ArrayList<>();
        for (int i=0; i<awardsVOList.size(); i++){
            Awards awards = BeanUtil.copyProperties(awardsVOList.get(i),Awards.class);
            awards.setRaffleId(raffle.getId());
            awardsList.add(awards);
        }
        saveBatch(awardsList);
    }

    public Map<String, AwardsVO> getMapByRaffle(List<String> raffleIds,String sort) {
        if (CollectionUtils.isEmpty(raffleIds)){
            return new HashMap<>();
        }
        Map<String, AwardsVO> awardsVOMap = new HashMap<>();
        QueryWrapper<Awards> queryAward = new QueryWrapper<>();
        queryAward.in("raffle_id",raffleIds);
        if (Objects.nonNull(sort)){
            queryAward.eq("sort",sort);
        }
        List<Awards> awardsList = this.list(queryAward);
        for (Awards awards : awardsList) {
            AwardsVO awardsVO = BeanUtil.copyProperties(awards,AwardsVO.class);
            awardsVOMap.put(awards.getRaffleId(),awardsVO);
        }
        return awardsVOMap;
    }

    public List<Awards> getListByRaffleId(String raffleId){
        QueryWrapper<Awards> queryAward = new QueryWrapper<>();
        queryAward.in("raffle_id",raffleId);
        return list(queryAward);
    }

    public void allocationAwards(Raffle raffle){
        List<AwardsUser> awardsUsers = new ArrayList<>();
        List<Awards> awards = getListByRaffleId(raffle.getId());
        awards.sort(Comparator.comparing(Awards::getSort));
        //参加总人数
        int allNum = raffle.getNumber();
        List<RaffleUser> listRaffleUser = raffleMapper.getListRaffleUser(raffle.getId(),"");
        //存储参加用户和编号
        Map<Integer,String> userids = new HashMap<>();
        List<String> ids = new ArrayList<>();
        for (RaffleUser raffleUser : listRaffleUser) {
            userids.put(raffleUser.getSort(),raffleUser.getUserId());
            ids.add(raffleUser.getUserId());
        }
        List<User> users = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(ids)){
            users = userService.listByIds(ids);
        }
        Map<String,User> userMap= new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(),user);
        }

        Random ran=new Random();
        //遍历所有奖项
        int remainder = allNum;
        for (Awards award : awards) {
            //当前抽奖人数大于该奖项时 正常抽奖
            if (remainder>award.getNumber()){
                for (int i=0; i<award.getNumber();i++){
                    int key=ran.nextInt(allNum)+1;
                    while (!userids.containsKey(key)){
                        if (key==allNum){
                            key=0;
                        }
                        key++;
                    }
                    AwardsUser awardsUser = new AwardsUser();
                    awardsUser.setUserId(userids.get(key));
                    awardsUser.setAwardId(award.getId());
                    awardsUser.setWinTime(raffle.getTime());
                    awardsUser.setRaffleId(raffle.getId());
                    awardsUser.setInitiator(raffle.getUserId());
                    awardsUser.setReport("0");
                    awardsUser.setHome(userMap.get(awardsUser.getUserId()).getHome());
                    awardsUser.setSign("0");

                    awardsUsers.add(awardsUser);
                    userids.remove(key);
                }
            }else{
                //抽奖人数小于奖品时
                userids.forEach((key,value)->{
                    AwardsUser awardsUser = new AwardsUser();
                    awardsUser.setUserId(userids.get(key));
                    awardsUser.setAwardId(award.getId());
                    awardsUser.setWinTime(raffle.getTime());
                    awardsUser.setRaffleId(raffle.getId());
                    awardsUser.setInitiator(raffle.getUserId());
                    awardsUser.setReport("0");
                    awardsUser.setHome(userMap.get(awardsUser.getUserId()).getHome());
                    awardsUser.setSign("0");
                    awardsUsers.add(awardsUser);
                });
                break;
            }
            remainder=remainder-award.getNumber();
        }
        if (CollectionUtils.isNotEmpty(awardsUsers))
            awardsMapper.addAwardsUser(awardsUsers);
    }

    @Override
    public List<AwardsUserVO> getAwardsByRaffle(String raffleId) {
        List<AwardsUserVO> list = new ArrayList<>();
        QueryWrapper<Awards> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("raffle_id",raffleId);
        List<Awards> awards = this.list(queryWrapper);
        for (Awards award : awards) {
            AwardsUserVO awardsUserVO = BeanUtil.copyProperties(award,AwardsUserVO.class);
            List<AwardsUser> awardsUsers = awardsMapper.selectAwardUser(award);
            List<String> userId = awardsUsers.stream().map(AwardsUser::getUserId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(userId)){
                List<User> users = userService.listByIds(userId);
                awardsUserVO.setUserList(users);
            }
            list.add(awardsUserVO);
        }

        return list;
    }

    @Override
    public List<AwardsUserGetVO> getAwardsByUser(AwardsUserGetVO vo) {
        List<AwardsUserGetVO> list = new ArrayList<>();
        List<AwardsUser> awardUserByUser = awardsMapper.getAwardUserByUser(vo);

        Map<String, User> userMap = userService.list().stream().collect(Collectors.toMap(User::getId, user -> user));
        Map<String, Awards> awardsMap = awardsService.list().stream().collect(Collectors.toMap(Awards::getId, awards -> awards));
        //补充信息
        for (AwardsUser awardsUser : awardUserByUser) {
            AwardsUserGetVO awardsUserGetVO = BeanUtil.copyProperties(awardsUser,AwardsUserGetVO.class);
            awardsUserGetVO.setNickname(userMap.get(awardsUserGetVO.getUserId()).getNickname());
            awardsUserGetVO.setInitiatorNickname(userMap.get(awardsUserGetVO.getInitiator()).getNickname());
            awardsUserGetVO.setPrizeName(awardsMap.get(awardsUser.getAwardId()).getPrizeName());

            awardsUserGetVO.setSign(SignEnum.ofCode(awardsUserGetVO.getSign()).getMessage());
            list.add(awardsUserGetVO);
        }
        return list;
    }

    @Override
    public void updateMyAward(AwardsUserGetVO awardsUserGetVO) {
        if (StringUtils.equals(awardsUserGetVO.getSign(),"1") && Objects.isNull(awardsUserGetVO.getGetAwardsTime())){
            awardsUserGetVO.setGetAwardsTime(LocalDateTime.now());
        }
        AwardsUser awardsUser = BeanUtil.copyProperties(awardsUserGetVO,AwardsUser.class);
        if (Objects.nonNull(SignEnum.ofName(awardsUserGetVO.getSign()))){
            awardsUser.setSign(SignEnum.ofName(awardsUserGetVO.getSign()).getCode());
        }
        awardsMapper.updateAwardsUser(awardsUser);
    }

    @Override
    public List<AwardsUserGetVO> getAllAwards() {
        List<AwardsUserGetVO> list = new ArrayList<>();
        List<AwardsUser> awardUserByUser = awardsMapper.getAllAwardUser();
        List<User> users = userService.list();
        Map<String,User> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(),user);
        }
        List<Awards> awards = awardsService.list();
        Map<String,Awards> awardsMap = new HashMap<>();
        for (Awards award : awards) {
            awardsMap.put(award.getId(),award);
        }
        for (AwardsUser awardsUser : awardUserByUser) {
            AwardsUserGetVO awardsUserGetVO = BeanUtil.copyProperties(awardsUser,AwardsUserGetVO.class);
            User user = userMap.get(awardsUserGetVO.getUserId());
            awardsUserGetVO.setNickname(user.getNickname());
            Awards award = awardsMap.get(awardsUser.getAwardId());
            awardsUserGetVO.setPrizeName(award.getPrizeName());
            awardsUserGetVO.setSign(SignEnum.ofCode(awardsUserGetVO.getSign()).getMessage());
            list.add(awardsUserGetVO);
        }
        return list;
    }

}
