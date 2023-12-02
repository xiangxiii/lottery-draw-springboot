package lottery.draw.springboot.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liux
 * @date 2023/11/9 14:28
 */

public enum RaffleTypeEnum {
    EXIT("0","普通抽奖"),
    START("1","实名抽奖"),
    OPEN("2","已签收"),

    ;
    private String code;

    private String message;
    private static final Map<String, RaffleTypeEnum> map = new HashMap<>(values().length, 1);
    private static final Map<String, RaffleTypeEnum> map2 = new HashMap<>(values().length, 1);


    static {
        for (RaffleTypeEnum c : values()) {
            map.put(c.code, c);
            map2.put(c.message, c);
        }
    }

    RaffleTypeEnum(String code, String message){
        this.code  = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static RaffleTypeEnum ofCode(String code) {
        RaffleTypeEnum result = map.get(code);
        return result;
    }

    public static RaffleTypeEnum ofName(String message) {
        RaffleTypeEnum result = map2.get(message);
        return result;
    }
}
