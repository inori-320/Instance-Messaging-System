package common;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author lty
 * 表示通讯时的消息对象
 */
public class Messages implements Serializable {
    @Serial
    private static final long serialVersionUID =  1L;
    private String sender;
    private String receiver;
    private String content;
    private String time;
    private String mesType;

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
