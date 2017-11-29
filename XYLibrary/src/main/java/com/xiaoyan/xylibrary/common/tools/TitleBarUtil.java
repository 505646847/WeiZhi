package com.xiaoyan.xylibrary.common.tools;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.xiaoyan.xylibrary.common.widget.other.TitleBar;

/**
 * 标题工具类
 */

public class TitleBarUtil {

  /**
   * 设置标题
   *
   * @param leftVisibility 左边是否可见（ture为可见）
   * @param title 标题名字
   * @param rightText 右边文字（为null则不显示）
   */
  public static void setTitle(TitleBar titleBar, boolean leftVisibility, String title,
      String rightText) {

    setTitle(titleBar, leftVisibility, title);

    if (TextUtils.isEmpty(rightText)) {
      titleBar.setRightLayoutVisibility(View.GONE);
    } else {
      titleBar.setRightText(rightText);
    }
  }

  /**
   * 设置标题
   *
   * @param leftVisibility 左边是否可见（ture为可见）
   * @param title 标题名字
   * @param rightDrawableId 右边图片（为0则不显示）
   */
  public static void setTitle(TitleBar titleBar, boolean leftVisibility, String title,
      int rightDrawableId) {

    setTitle(titleBar, leftVisibility, title);

    if (rightDrawableId == 0) {
      titleBar.setRightLayoutVisibility(View.GONE);
    } else {
      titleBar.setRightImageResource(rightDrawableId);
    }
  }

  /**
   * 设置右布局点击事件
   */
  public static void setRightClick(TitleBar titleBar, OnClickListener onClickListener) {
    titleBar.setRightLayoutClickListener(onClickListener);
  }

  /**
   * 设置左布局点击事件
   */
  public static void setLeftClick(TitleBar titleBar, OnClickListener onClickListener) {
    titleBar.setLeftLayoutClickListener(onClickListener);
  }
  public static void setTitle(TitleBar titleBar, boolean leftVisibility, String title) {
    if (leftVisibility) {
      titleBar.setBackVisibility(View.VISIBLE);
    } else {
      titleBar.setBackVisibility(View.GONE);
    }

    titleBar.setTitle(title);
  }

  public static void setTitle(TitleBar titleBar, String leftText, String title) {
    if (TextUtils.isEmpty(leftText)) {
      titleBar.setLeftLayoutVisibility(View.GONE);
    } else {
      titleBar.setLeftText(leftText);
    }

    titleBar.setTitle(title);
  }

  public static void setTitle(TitleBar titleBar, String leftText, String title, String rightText) {
    setTitle(titleBar, leftText, title);

    if (TextUtils.isEmpty(rightText)) {
      titleBar.setRightLayoutVisibility(View.GONE);
    } else {
      titleBar.setRightText(rightText);
    }
  }
  public static void setTitle(TitleBar titleBar, String leftText, String title, int rightDrawableId) {
    setTitle(titleBar, leftText, title);

    if (rightDrawableId == 0) {
      titleBar.setRightLayoutVisibility(View.GONE);
    } else {
      titleBar.setRightImageResource(rightDrawableId);
    }
  }
}
