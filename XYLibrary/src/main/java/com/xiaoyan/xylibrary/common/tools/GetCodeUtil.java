package com.xiaoyan.xylibrary.common.tools;

import android.os.Handler;
import android.os.Message;
import com.xiaoyan.xylibrary.common.listener.OnGetCodeCallBack;

/**
 * 获取验证码工具类
 */

public class GetCodeUtil {

  private final int code = 0;//用于标识获得验证码

  private int codeNum;//记录倒计时
  private OnGetCodeCallBack callBack;//获取验证码回调

  Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
//      super.handleMessage(msg);
      switch (msg.what) {
        case code:
          if (codeNum > 0) {
            if (callBack != null) {
              callBack.onCode(codeNum--);
            }
            handler.sendEmptyMessageDelayed(code, 1000);
          }else {
            if (callBack != null) {
              callBack.onFinish();
            }
          }
          break;
      }
    }
  };

  public void getCode(int codeNum, OnGetCodeCallBack callBack) {
    this.codeNum = codeNum;
    this.callBack = callBack;
    handler.sendEmptyMessage(code);

  }

  /**
   * 关闭获取验证码
   */
  public void closeGetCode() {
    if (handler.hasMessages(code)) {
      handler.removeMessages(code);
    }
  }
}
