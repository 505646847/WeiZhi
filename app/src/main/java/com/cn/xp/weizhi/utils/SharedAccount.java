package com.cn.xp.weizhi.utils;

import android.content.Context;

import com.xiaoyan.xylibrary.common.tools.StringUtil;

import java.util.Calendar;

public class SharedAccount {

  private static SharedPreferencesUtil share;

  private SharedAccount() {
  }

  private static SharedAccount instance = null;
  private final String USERID_KEY = "userId";
  private final String SESSION_KEY = "sessionId";
  private final String TOKEN_KEY = "token";
  private final String TIME_KEY = "time";
  private final String LOGINTYPE_KEY = "logintype";
  private final String MOBILE_KEY = "mobile";
  private final String PASSWORD_KEY = "password";
  private final String TYPE_KEY = "type";
  private final String IDS_KEY = "ids";
  private final String UNION_ID_KEY = "unionId";
  private final String ISFIRST = "isfirst";
  private final String WX_HEAD = "head";
  private final String WX_HEAD_FILE_PATH = "headPath";
  private final String WX_NICK = "nick";

  public static SharedAccount getInstance(Context context) {
    if (instance == null) {
      instance = new SharedAccount();
    }
    share = SharedPreferencesUtil.getInstance(context, "account");
    return instance;
  }

  public boolean isHaveAccount() {
    boolean a_p = !StringUtil.isEmpty(getMobile()) && !StringUtil.isEmpty(getPassword());
    boolean o_l = getType() != 0 && !StringUtil.isEmpty(getIds());
    if (a_p || o_l) {
      return true;
    }
    return false;
  }

  public void save(String mobile, String password) {
    share.putInt(LOGINTYPE_KEY, 1);
    share.putString(MOBILE_KEY, mobile);
    share.putString(PASSWORD_KEY, password);
    share.commit();
  }
  public void clearPsw() {
    share.remove(PASSWORD_KEY);
    share.commit();
  }
  public void saveWXInfo(String unionId,String strHead, String strNick) {
    share.putInt(LOGINTYPE_KEY, 1);
    share.putString(UNION_ID_KEY, unionId);
    share.putString(WX_HEAD, strHead);
    share.putString(WX_NICK, strNick);
    share.commit();
  }

  public void saveWXHeadPath(String headPath) {
    share.putString(WX_HEAD_FILE_PATH, headPath);
    share.commit();
  }

  public void save(String sessionId, String token, String userId) {
    share.putInt(LOGINTYPE_KEY, 1);
    share.putString(USERID_KEY, userId);
    share.putString(SESSION_KEY, sessionId);
    share.putString(TOKEN_KEY, token);
    share.putLong(TIME_KEY, Calendar.getInstance().getTimeInMillis());
    share.commit();
  }

  public void save(int type, String ids) {
    share.putInt(LOGINTYPE_KEY, 2);
    share.putInt(TYPE_KEY, type);
    share.putString(IDS_KEY, ids);
    share.commit();
  }

  public void save(boolean isFirst) {
    share.putBoolean(ISFIRST, isFirst);
    share.commit();
  }

  public boolean getIsFirst() {
    return share.getBoolean(ISFIRST, false);
  }

  public void delete() {
    share.remove(USERID_KEY);
    share.remove(TIME_KEY);
    share.remove(SESSION_KEY);
    share.remove(TOKEN_KEY);
    share.remove(LOGINTYPE_KEY);
    share.remove(MOBILE_KEY);
    share.remove(PASSWORD_KEY);
    share.remove(TYPE_KEY);
    share.remove(IDS_KEY);
    share.remove(UNION_ID_KEY);
    share.remove(WX_HEAD);
    share.remove(WX_NICK);
    share.remove(WX_HEAD_FILE_PATH);
    share.commit();
  }

  public int getLoginType() {
    return share.getInt(LOGINTYPE_KEY, 0);
  }

  public int getType() {
    return share.getInt(TYPE_KEY, 0);
  }

  public String getIds() {
    return share.getString(IDS_KEY, "");
  }

  public String getMobile() {
    return share.getString(MOBILE_KEY, "");
  }

  public String getPassword() {
    return share.getString(PASSWORD_KEY, "");
  }

  public String getSessionId() {
    return share.getString(SESSION_KEY, "");
  }

  public String getToken() {
    return share.getString(TOKEN_KEY, "");
  }

  public long getTime() {
    return share.getLong(TIME_KEY, 0l);
  }

  public String getUserId() {
    return share.getString(USERID_KEY, "");
  }

  public String getUnionId() {
    return share.getString(UNION_ID_KEY, "");
  }

  public String getWXHead() {
    return share.getString(WX_HEAD, "");
  }

  public String getWXNick() {
    return share.getString(WX_NICK, "");
  }

  public String getWXHeadPath() {
    return share.getString(WX_HEAD_FILE_PATH, "");
  }
}
