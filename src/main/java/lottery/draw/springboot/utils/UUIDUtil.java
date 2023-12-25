package lottery.draw.springboot.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * @title: UUIDUtil
 * @projectName ips-parent
 * @description: uuid工具类
 * @author zhy
 * @date 2020/3/412:17
 */
public class UUIDUtil {

    private UUIDUtil(){}

    /**
      * @description: 获取uuid
      * @throws
      * @return
      * @author zhy
      * @date 2020/3/4 12:18
      */
    public static String getUUID(){
        UUID uuid  =  UUID.randomUUID();
        return uuid.toString().replaceAll("\\-", "");
    }

    /**
     *@Description 获取8位随机
     *@Params
     *@Return
     *@Author  Jiatc
     *@Date  2020/7/7
     */
    public static String getUUID6() {
        String SYMBOLS = "0123456789";
        Random RANDOM = new SecureRandom();
        char[] nonceChars = new char[6];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

}
