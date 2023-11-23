package lottery.draw.springboot.enums;

/**
 * @author liux
 * @date 2023/11/9 14:28
 */

public enum  RoleEnum {
    ADMIN("1","管理员"),
    USER("2","普通用户"),
    AUTHENTICATE_USER("3","认证账号"),
    BAN_USER("4","封禁账号"),

    ;
    private String code;

    private String message;

    RoleEnum(String code,String message){
        this.code  = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
