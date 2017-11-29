package com.cn.xp.weizhi.bean;

import org.json.JSONObject;

public class UserData {

	private UserData(){}
	
	private static UserData instance;
	public static UserData getInstance(){
		if (instance == null) {
			instance = new UserData();
		}
		return instance;
	}
	
	private JSONObject userObj;
	private String sessionId = "";
	private boolean isLogin;
	private long userId;
	private String createTime;

	public void setUserObj(JSONObject userObj) {
		this.userObj = userObj;
	}
	
	public JSONObject getUserObj() {
		return userObj;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public void clear(){
		setLogin(false);
		setSessionId(null);
		setUserObj(null);
		setUserId(0);
		setCreateTime(null);
	}
}