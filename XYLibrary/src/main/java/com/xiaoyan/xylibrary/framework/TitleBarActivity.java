package com.xiaoyan.xylibrary.framework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.xiaoyan.xylibrary.R;
import com.xiaoyan.xylibrary.common.tools.TitleBarUtil;
import com.xiaoyan.xylibrary.common.widget.other.TitleBar;

/**
 * 默认具有标题的Activity
 */

public abstract class TitleBarActivity extends BaseActivity {

  protected TitleBar titleBar;

  @Override
  protected View layoutView() {
    LinearLayout linearLayout = (LinearLayout) inflateLayout(R.layout.activity_title_bar);
    titleBar = (TitleBar) linearLayout.findViewById(R.id.titleBar);
    linearLayout.addView(LayoutInflater.from(this).inflate(layoutResID(), linearLayout, false));
    return linearLayout;
  }

  protected abstract int layoutResID();

  protected abstract void initTitle();

  @Override
  protected void InitView() {
    initTitle();
    init();
  }

  protected abstract void init();

  /**
   * 设置标题
   *
   * @param leftVisibility 左边是否可见（ture为可见）
   * @param title 标题名字
   * @param rightText 右边文字（为null则不显示）
   */
  protected void setTitle(boolean leftVisibility, String title, String rightText) {

    TitleBarUtil.setTitle(titleBar, leftVisibility, title, rightText);
  }

  /**
   * 设置标题
   *
   * @param leftVisibility 左边是否可见（ture为可见）
   * @param title 标题名字
   * @param rightDrawableId 右边图片（为0则不显示）
   */
  protected void setTitle(boolean leftVisibility, String title, int rightDrawableId) {

    TitleBarUtil.setTitle(titleBar, leftVisibility, title, rightDrawableId);
  }

  /**
   * 设置右布局点击事件
   */
  protected void setRightClick(OnClickListener onClickListener) {
    TitleBarUtil.setRightClick(titleBar, onClickListener);
  }
  /**
   * 设置左布局点击事件
   */
  protected void setLeftClick(OnClickListener onClickListener) {
    TitleBarUtil.setLeftClick(titleBar, onClickListener);
  }
  protected void setTitle(String title) {
    setTitle(false, title, "");
  }

  protected void setTitle(boolean leftVisibility, String title) {
    setTitle(leftVisibility, title, "");
  }

  protected void setTitle(String leftText, String title) {
    setTitle(leftText, title, "");
  }

  protected void setTitle(String leftText, String title, String rightText) {
    TitleBarUtil.setTitle(titleBar, leftText, title, rightText);
  }
  protected void  setTitle(String leftText, String title, int rightDrawableId) {
    TitleBarUtil.setTitle(titleBar, leftText, title, rightDrawableId);
  }
}
