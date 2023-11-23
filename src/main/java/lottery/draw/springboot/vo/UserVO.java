package lottery.draw.springboot.vo;

import lombok.Data;


@Data
public class UserVO {
    private String id;
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String salt;
    private String role;
    private String sex;
    private String phone;
}
