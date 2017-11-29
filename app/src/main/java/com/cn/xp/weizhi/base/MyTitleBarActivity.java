package com.cn.xp.weizhi.base;

import android.text.TextUtils;

import com.cn.xp.weizhi.bean.UserData;
import com.cn.xp.weizhi.utils.DialogUtil;
import com.xiaoyan.xylibrary.framework.TitleBarActivity;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public abstract class MyTitleBarActivity extends TitleBarActivity {
  @Override
  public void onResume() {
    super.onResume();
  }
  @Override
  public void onPause() {
    super.onPause();
  }
  public void showOtherLoginDialog() {
    if (this==null){
      return;
    }
    DialogUtil util = new DialogUtil(this);
    util.showOntherLoginDialog();
  }

  public String getSessionId() {
    UserData userData = UserData.getInstance();
    String strSessionId = userData.getSessionId();
    if (TextUtils.isEmpty(strSessionId)) {
      showOtherLoginDialog();
    }
    return strSessionId;
  }
}
