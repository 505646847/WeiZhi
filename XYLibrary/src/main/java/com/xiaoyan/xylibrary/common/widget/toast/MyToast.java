package com.xiaoyan.xylibrary.common.widget.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * 自定义Toast
 */
public class MyToast {

  private MyToast() {

  }

  private static final Object SYNC_LOCK = new Object();
  private static Toast mToast;
  /**
   * 上下文
   */
  public static Context context;

  public static Context getContext() {
    return context;
  }

  public static void setContext(Context context) {
    MyToast.context = context;
  }

  /**
   * 获取toast环境，为toast加锁
   */
  private static void initToastInstance() {

    if (mToast == null) {
      synchronized (SYNC_LOCK) {
        if (mToast == null) {
          mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
      }
    }
  }

  /**
   * 展示吐司
   *
   * @param context 环境
   * @param text 内容
   */
  public static void showToast(Context context, String text) {
    setContext(context);
    if (getContext() != null && text != null) {
      initToastInstance();
      mToast.setDuration(Toast.LENGTH_SHORT);
      mToast.setText(text);
      mToast.show();
    }
  }

  /**
   * 显示Toast
   *
   * @param context 环境
   * @param text 内容
   * @param isLength_Long 是否长时间展示
   */
  public static void showToast(String text, boolean isLength_Long,
      Context context) {
    setContext(context);
    if (getContext() != null && text != null) {
      if (isLength_Long) {
        initToastInstance();
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(text);
        mToast.show();
      } else {
        showToast(getContext(), text);
      }
    }

  }
}
