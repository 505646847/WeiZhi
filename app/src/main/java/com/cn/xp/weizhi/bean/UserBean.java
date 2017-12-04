package com.cn.xp.weizhi.bean;

import java.util.List;

/**
 * author: cjf
 * 用户信息UserBean
 * date: on 2017/12/1
 */

public class UserBean {
    private int code;
    private DataBean data;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "code=" + code +
                ", data=" + data +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static class DataBean{
        private String ryToken;
        private String mobile;
        private String sessionId;
        private int type;
        private int fz;
        private String createTime;
        private String loginIp;
        private int id;
        private int isRealName;
        private String email;
        private String account;
        private boolean isComplete;

        @Override
        public String toString() {
            return "DataBean{" +
                    "ryToken='" + ryToken + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", sessionId='" + sessionId + '\'' +
                    ", type=" + type +
                    ", fz=" + fz +
                    ", createTime='" + createTime + '\'' +
                    ", loginIp='" + loginIp + '\'' +
                    ", id=" + id +
                    ", isRealName=" + isRealName +
                    ", email='" + email + '\'' +
                    ", account='" + account + '\'' +
                    ", isComplete=" + isComplete +
                    '}';
        }

        public String getRyToken() {
            return ryToken;
        }

        public void setRyToken(String ryToken) {
            this.ryToken = ryToken;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getFz() {
            return fz;
        }

        public void setFz(int fz) {
            this.fz = fz;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLoginIp() {
            return loginIp;
        }

        public void setLoginIp(String loginIp) {
            this.loginIp = loginIp;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsRealName() {
            return isRealName;
        }

        public void setIsRealName(int isRealName) {
            this.isRealName = isRealName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public boolean isComplete() {
            return isComplete;
        }

        public void setComplete(boolean complete) {
            isComplete = complete;
        }
    }
}
