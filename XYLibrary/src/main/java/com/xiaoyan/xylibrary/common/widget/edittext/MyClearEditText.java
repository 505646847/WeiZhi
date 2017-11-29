package com.xiaoyan.xylibrary.common.widget.edittext;

import com.xiaoyan.xylibrary.R;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * 自定义一个具有清除功能的editText
 *
 * @author xiejinxiong
 *
 */
public class MyClearEditText extends EditText implements TextWatcher {
	/** 储存清除的图片 */
	private Drawable drawClear;
	/** 储存平常状态的标识的图片 */
	private Drawable drawIDNormal;
	/** 储存受焦点状态的标识的图片 */
	private Drawable drawIDPress;

	public MyClearEditText(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public MyClearEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
		// TODO Auto-generated constructor stub
	}

	public MyClearEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initClearDrawable();
		this.addTextChangedListener(this);
	}

	public void setDrawIDNormal(Drawable drawIDNormal) {
		this.drawIDNormal = drawIDNormal;
		this.setCompoundDrawablesWithIntrinsicBounds(drawIDNormal, null,
				null, null);
	}

	public void setDrawIDPress(Drawable drawIDPress) {
		this.drawIDPress = drawIDPress;
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
								  Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		// 判断焦点失去和得到时的操作
		if (focused && !this.getText().toString().equals("")) {
			this.setCompoundDrawablesWithIntrinsicBounds(drawIDPress, null,
					drawClear, null);
		} else if (focused) {
			this.setCompoundDrawablesWithIntrinsicBounds(drawIDPress, null,
					null, null);
		} else {
			this.setCompoundDrawablesWithIntrinsicBounds(drawIDNormal, null,
					null, null);
		}
	}

	/**
	 * 初始化清除的图片
	 */
	private void initClearDrawable() {
		drawClear = getCompoundDrawables()[2];

		// 判断清除的图片是否为空
		if (drawClear == null) {
			drawClear = getResources().getDrawable(R.drawable.editdelete);
		}

		// drawIDNormal =
		// getResources().getDrawable(R.drawable.number_grey_icon);
		// drawIDPress =
		// getResources().getDrawable(R.drawable.number_blue_icon);

		if (drawIDNormal != null && drawIDPress != null) {
			this.setCompoundDrawablesWithIntrinsicBounds(drawIDNormal, null,
					null, null);
		} else {
			// 将输入框默认设置为没有清除的按钮
			this.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}

	}

	public void onTextChanged(CharSequence text, int start, int lengthBefore,
							  int lengthAfter) {
		// 判断输入框中是否有内容
		if (text.length() > 0) {
			this.setCompoundDrawablesWithIntrinsicBounds(drawIDPress, null,
					drawClear, null);
		} else {
			this.setCompoundDrawablesWithIntrinsicBounds(drawIDPress, null,
					null, null);
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
								  int after) {
		// TODO Auto-generated method stub

	}

	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 判断触碰是否结束
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// 判断所触碰的位置是否为清除的按钮
			if (event.getX() > (getWidth() - getTotalPaddingRight())
					&& event.getX() < (getWidth() - getPaddingRight())) {
				// 将editText里面的内容清除
				setText("");
			}
		}
		return super.onTouchEvent(event);
	}
}
