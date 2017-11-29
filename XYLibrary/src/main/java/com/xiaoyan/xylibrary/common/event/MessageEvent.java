package com.xiaoyan.xylibrary.common.event;

/**
 * 消息事件
 *
 * @author Jinxiong.Xie
 * @time 2017/4/20 0:07
 */
public class MessageEvent {
    private static int messageIndex = 0;

    public static final int UPDATE_USER_INFO = messageIndex++;//更新用户信息

    private int id;
    private Object[] message;

    public MessageEvent(int id, Object... message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object[] getMessage() {
        return message;
    }

    public void setMessage(Object[] message) {
        this.message = message;
    }
}
