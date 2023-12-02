package lottery.draw.springboot.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liux
 * @date 2023/11/9 14:28
 */

public enum StateEnum {
    EXIT("1","编辑状态"),
    START("2","进行中"),
    OPEN("3","已开奖"),
    END("4","结束"),

    ;
    private String code;

    private String message;
    private static final Map<String, StateEnum> map = new HashMap<>(values().length, 1);
    private static final Map<String, StateEnum> map2 = new HashMap<>(values().length, 1);


    static {
        for (StateEnum c : values()) {
            map.put(c.code, c);
            map2.put(c.message, c);
        }
    }

    StateEnum(String code, String message){
        this.code  = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static StateEnum ofCode(String code) {
        StateEnum result = map.get(code);
        return result;
    }

    public static StateEnum ofName(String message) {
        StateEnum result = map2.get(message);
        return result;
    }
}
