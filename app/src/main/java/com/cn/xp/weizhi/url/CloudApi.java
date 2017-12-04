package com.cn.xp.weizhi.url;

/**
 * Created by Administrator on 2017/7/12.
 */

public class CloudApi {

  	public static String IP = "120.26.216.130";

    public static String SERVLET_TRL = "http://"+IP+"/zhengqi/api/user";


   //注册获取验证码
     public static String register_getcode_url = SERVLET_TRL+"/code";
    //注册
     public static String register_url = SERVLET_TRL + "/register";

    //账号登录
     public static String login_account_url = SERVLET_TRL + "/login";

    //验证码登录
     public static String login_verificationCode_url = SERVLET_TRL + "/loginByCode";

}
