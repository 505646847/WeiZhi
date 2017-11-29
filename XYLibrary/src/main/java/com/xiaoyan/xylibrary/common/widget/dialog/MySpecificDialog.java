package com.xiaoyan.xylibrary.common.widget.dialog;

import com.xiaoyan.xylibrary.R;
import com.xiaoyan.xylibrary.common.tools.MyPhonePixels;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * 自定义Dialog
 *
 * @author xiejinxiong
 *
 */
public class MySpecificDialog extends Dialog {

	/** 屏幕高度像素 */
	// private int height;
	/** 屏幕宽度像素 */
	private int width;
	/** 自定义对话框 */
	private MySpecificDialog myDialog;
	/** 对话框中的内容 */
	private TextView tvMessage;
	/** 对话框中的左键 */
	private TextView tvLeft;
	/** 对话框中的右键 */
	private TextView tvRight;
	// /** 显示Dialog的Activity */
	// private Activity activity;
	/** 上下文 */
	private Context context;
	/** dialog左右键的回调类 */
	private MyDialogCallBack myDialogCallBack;
	/** dialog中间键回调类 */
	private MyDialogCallBackCenter myDialogCallBackCenter;

	public MySpecificDialog(Context context) {
		this(context, R.style.MyDialogStyleBottom);
	}

	public MySpecificDialog(Context context, int theme) {
		super(context, theme);
		// this.activity = (Activity) context;
		this.context = context;
	}

	/**
	 * 初始化屏幕像素
	 */
	public void InitPixels() {
		MyPhonePixels myPhonePixels = new MyPhonePixels(this.context);
		width = myPhonePixels.getWidthPixels();
		// DisplayMetrics metric = new DisplayMetrics();
		// this.activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		// height = metric.heightPixels;
		// width = metric.widthPixels;
	}

	/**
	 * 初始化对话框（两个按钮）
	 *
	 * @param context
	 * @param strMeaasge
	 * @param strLeft
	 * @param strRight
	 */
	public void showDialog(String strMeaasge, String strLeft, String strRight,
						   MyDialogCallBack myDialogCallBack) {

		this.myDialogCallBack = myDialogCallBack;
		// myDialog = new MyDialog(context);
		myDialog = this;
		InitPixels();
		initDialogUI(this.context);
		initDialogInfo(strMeaasge, strLeft, strRight);

		myDialog.setCanceledOnTouchOutside(false);
		myDialog.show();
	}

	/**
	 * 初始化对话框（单个按钮）
	 *
	 * @param strMeaasge
	 * @param strCenter
	 * @param myDialogCallBackCenter
	 */
	public void showDialog(String strMeaasge, String strCenter,
						   MyDialogCallBackCenter myDialogCallBackCenter) {

		this.myDialogCallBackCenter = myDialogCallBackCenter;
		showDialog(strMeaasge, strCenter, "", null);
	}

	/**
	 * 初始化UI
	 */
	private void initDialogUI(Context context) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_layout, null, false);
		initDialogListener(view);

		Window window = myDialog.getWindow();
		window.setContentView(view);
		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
		p.height = LayoutParams.WRAP_CONTENT;
		p.width = (int) (width * 0.8); // 宽度设置为屏幕的0.8
		window.setAttributes(p);
	}

	/**
	 * 初始化普通对话框并显示内容
	 *
	 * @param view
	 */
	private void initDialogListener(View view) {
		// 对话框中的内容
		tvMessage = (TextView) view.findViewById(R.id.tvmessage);

		// 对话框中的左键
		tvLeft = (TextView) view.findViewById(R.id.tvleft);

		tvLeft.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// dialogLeftBtnFunction();
				if (myDialogCallBack != null) {
					myDialogCallBack.onLeftBtnFun();
				} else {
					myDialogCallBackCenter.onCenterBtnFun();
				}

				myDialog.dismiss();
			}
		});

		// 对话框中的右键
		tvRight = (TextView) view.findViewById(R.id.tvright);
		if (myDialogCallBack != null) {
			tvRight.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// dialogRightBtnFunction();
					myDialogCallBack.onRightBtnFun();
					myDialog.dismiss();
				}
			});
		} else {
			tvRight.setVisibility(View.GONE);
			View viewCenter = view.findViewById(R.id.viewcenter);
			viewCenter.setVisibility(View.GONE);
		}
	}

	/**
	 * 关闭dialog
	 */
	public void closeDialog() {
		if (myDialog != null) {
			myDialog.dismiss();
		}
	}

	/**
	 * 设置dialog的信息
	 *
	 * @param strMeaasge
	 * @param strLeft
	 * @param strRight
	 */
	private void initDialogInfo(String strMeaasge, String strLeft,
								String strRight) {
		tvMessage.setText(strMeaasge);
		tvLeft.setText(strLeft);
		tvRight.setText(strRight);
	}

	/**
	 * 用于实现dialog左右按钮的点击功能
	 *
	 * @author xiejinxiong
	 *
	 */
	public interface MyDialogCallBack {

		/**
		 * dialog左键功能
		 */
		void onLeftBtnFun();

		/**
		 * dialog右键功能
		 */
		void onRightBtnFun();
	}

	/**
	 * 用于实现dialog中间键按钮的点击功能
	 *
	 * @author xiejinxiong
	 *
	 */
	public interface MyDialogCallBackCenter {

		/**
		 * dialog中间键功能
		 */
		void onCenterBtnFun();

	}

}
