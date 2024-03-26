package lottery.draw.springboot.entity;

/**
 * @author liux
 * @date 2023/12/28 18:46
 */
public class WechatTokenEntity {
    /**
     * 接口访问凭证֤
     */
    private String accessToken;
    /**
     * 接口访问凭证֤，刷新
     */
    private String session_key;
    /**
     * 凭证有效期单位：second
     */
    private int expiresIn;
    /**
     * 授权用户唯一标识
     */
    private String openid;
    /**
     * 微信用户唯一标识
     */
    private String unionId;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
}


