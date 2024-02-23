package common;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author lty
 * 表示用户类
 */
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String userId;
    private String passwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWord() {
        return passwd;
    }

    public void setPassWord(String passwd) {
        this.passwd = passwd;
    }
}
