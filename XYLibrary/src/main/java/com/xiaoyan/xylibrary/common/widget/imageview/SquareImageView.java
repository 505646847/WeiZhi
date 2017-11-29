package com.xiaoyan.xylibrary.common.widget.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 宽高相同的ImageView
 */

public class SquareImageView extends ImageView {

  public SquareImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SquareImageView(Context context) {

    super(context);

  }

  @Override

  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),

        getDefaultSize(0, heightMeasureSpec));

    int childWidthSize = getMeasuredWidth();

    int childHeightSize = getMeasuredHeight();

// 高度和宽度一样

    heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(

        childWidthSize, MeasureSpec.EXACTLY);

    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

  }

}