package lottery.draw.springboot.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liux
 * @date 2023/11/9 14:28
 */

public enum SignEnum {
    WFH("0","未发货"),
    YFH("1","已发货"),
    YQS("-1","已签收"),

    ;
    private String code;

    private String message;
    private static final Map<String, SignEnum> map = new HashMap<>(values().length, 1);
    private static final Map<String, SignEnum> map2 = new HashMap<>(values().length, 1);


    static {
        for (SignEnum c : values()) {
            map.put(c.code, c);
            map2.put(c.message, c);
        }
    }

    SignEnum(String code, String message){
        this.code  = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static SignEnum ofCode(String code) {
        return map.get(code);
    }

    public static SignEnum ofName(String message) {
        return map2.get(message);
    }
}
