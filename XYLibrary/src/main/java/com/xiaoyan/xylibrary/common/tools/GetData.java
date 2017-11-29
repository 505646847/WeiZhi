package com.xiaoyan.xylibrary.common.tools;

import android.content.Context;

/**
 * 联网判断获取日期
 * @author Administrator
 * @since 2017/3/23.
 */

public abstract class GetData {

  protected Context context;
  protected NetworkStatus networkStatus;

  public GetData(Context context) {
    this.context = context;
    networkStatus = new NetworkStatus(context);
  }

  public void getData() {
    if (networkStatus.isNetAvilable()) {
      getFromNet();
    } else {
      getFromNoNet();
    }
  }

  public abstract void getFromNet();

  public abstract void getFromNoNet();
}