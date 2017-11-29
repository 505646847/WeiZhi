package com.cn.xp.weizhi.url;

/**
 * Created by Administrator on 2017/7/12.
 */

public class CloudApi {

//  	public static String IP = "183.60.125.125:8080"; // 183.60.125.125:8080 ,
//  	public static String IP = "weixinjl.s1.natapp.cc"; // 183.60.125.125:8080 ,
  	public static String IP = "120.26.216.130"; // 183.60.125.125:8080 ,

//  public static String IP = "192.168.1.183"; // 183.60.125.125:8080 ,
//  http://weixinjl.s1.natapp.cc/mishiquliao
//  http://weixinjl.s1.natapp.cc/mishiquliao/upload/ads/20170802/1501661576866_156601.png


    public static String SERVLET_TRL = "http://"+IP+"/zhengqi/api/user";

//  public static String RONG_CLOUD_TOKEN_URL = "http://api.cn.ronghub.com/user/getToken.json";
//
//  public static String SERVLET_URL_IMAGE = "http://" + IP + "/mishiquliao/";
//  public static String SERVLET_URL_IMAGE2 = "http://" + IP + "/mishiquliao";
//  public static String SERVLET_URL = SERVLET_URL_IMAGE + "api/";
   //注册获取验证码
     public static String register_getcode_url = SERVLET_TRL+"/code";
    //注册
     public static String register_url = SERVLET_TRL + "/register";

    //账号登录
     public static String login_account_url = SERVLET_TRL + "/login";

    //验证码登录
     public static String login_verificationCode_url = SERVLET_TRL + "/loginByCode";
  /**
   * 获取0首页 1启动页 2引导页
   */
//  public static String adsList = SERVLET_URL + "ads/list";

}
