package lottery.draw.springboot.dto;

import lombok.Data;


@Data
public class UserDTO {
    private String name;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String salt;
    private String role;

}
