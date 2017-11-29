package com.cn.xp.weizhi.base;

import android.text.TextUtils;

import com.cn.xp.weizhi.bean.UserData;
import com.cn.xp.weizhi.utils.DialogUtil;
import com.xiaoyan.xylibrary.framework.BaseFragment;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public abstract class MyBaseFragment extends BaseFragment {

  public void showOtherLoginDialog() {
    if (getActivity()==null){
      return;
    }
    DialogUtil util = new DialogUtil(getActivity());
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
