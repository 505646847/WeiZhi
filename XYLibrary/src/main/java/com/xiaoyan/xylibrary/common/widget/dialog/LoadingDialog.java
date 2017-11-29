package com.xiaoyan.xylibrary.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaoyan.xylibrary.R;

public class LoadingDialog {

	public Dialog mDialog;
	public TextView dialog_title;
	public TextView dialog_message;
	public TextView positive;
	public TextView negative;
	public ImageView imageView;
	private AnimationDrawable animationDrawable;

	public LoadingDialog(Context context, String title, String message) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.d_loading, null);
		mDialog = new Dialog(context, R.style.dialog);
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(true);
		imageView = (ImageView) view.findViewById(R.id.progress_view);
		imageView.setImageResource(R.drawable.loading_animation);
		animationDrawable = (AnimationDrawable) imageView.getDrawable();
		animationDrawable.start();

	}

	public void show() {
		if (mDialog != null) {
			mDialog.show();
		}
	}

	public void dismiss() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

}
