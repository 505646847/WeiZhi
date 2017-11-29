package com.xiaoyan.xylibrary.common.listener;

/**
 * 获取验证码回调
 */

public interface OnGetCodeCallBack {

  void onCode(int num);
  void onFinish();
}
