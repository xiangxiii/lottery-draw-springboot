package lottery.draw.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lottery.draw.springboot.entity.User;
import lottery.draw.springboot.service.IUserService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtils {

    private static IUserService staticuserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService(){
        staticuserService=userService;
    }

    /**
     * 生成token
     * return
     */
    public static String genToken(String userId, String sign){
        return JWT.create().withAudience(userId)
                    .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                    .sign(Algorithm.HMAC256(sign));
    }

    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)){
                String userId=JWT.decode(token).getAudience().get(0);
                return staticuserService.getById(Integer.valueOf(userId));
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
