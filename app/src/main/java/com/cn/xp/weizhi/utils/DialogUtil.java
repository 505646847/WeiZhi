package com.cn.xp.weizhi.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

import com.cn.xp.weizhi.MainActivity;
import com.cn.xp.weizhi.bean.UserData;
import com.xiaoyan.xylibrary.common.tools.ActivitiesManager;
import com.xiaoyan.xylibrary.common.widget.dialog.MySpecificDialog;
import com.xiaoyan.xylibrary.common.widget.dialog.MySpecificDialog.MyDialogCallBackCenter;
import com.xiaoyan.xylibrary.framework.BaseActivity;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class DialogUtil {

  private Context context;

  public DialogUtil(Context context) {
    this.context = context;
  }

  /**
   * 显示重新登录对话框
   */
  public void showOntherLoginDialog() {
    //监听系统的按键
    OnKeyListener keylistener = new OnKeyListener() {
      public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
          return true;
        } else {
          return false;
        }
      }
    };
    //消除用户信息
    UserData userData = UserData.getInstance();
    userData.clear();
    SharedAccount.getInstance(context).delete();
    //加载对话框
    MySpecificDialog dialog = new MySpecificDialog(context);
    dialog.setOnKeyListener(keylistener);
    dialog.setCancelable(false);
    dialog.showDialog("您的账号验证信息已过期，请重新登录", "确定", new MyDialogCallBackCenter() {
      @Override
      public void onCenterBtnFun() {
        ActivitiesManager.getInstance().popAllActivity();
        ((BaseActivity) context).BaseStartAct(MainActivity.class);
      }
    });
  }
}
