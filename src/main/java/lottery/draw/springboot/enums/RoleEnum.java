package lottery.draw.springboot.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liux
 * @date 2023/11/9 14:28
 */

public enum  RoleEnum {
    ADMIN("1","管理员"),
    USER("2","普通用户"),
    BAN_USER("3","封禁用户"),
    AUTHENTICATE_USER("4","认证用户"),

    ;
    private String code;

    private String message;

    private static final Map<String, RoleEnum> map = new HashMap<>(values().length, 1);
    private static final Map<String, RoleEnum> map2 = new HashMap<>(values().length, 1);


    static {
        for (RoleEnum c : values()) {
            map.put(c.code, c);
            map2.put(c.message, c);
        }
    }

    RoleEnum(String code, String message){
        this.code  = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static RoleEnum ofCode(String code) {
        RoleEnum result = map.get(code);
        return result;
    }

    public static RoleEnum ofName(String message) {
        RoleEnum result = map2.get(message);
        return result;
    }
}
