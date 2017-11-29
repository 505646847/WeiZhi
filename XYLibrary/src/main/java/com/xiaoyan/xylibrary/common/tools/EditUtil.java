package com.xiaoyan.xylibrary.common.tools;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import com.xiaoyan.xylibrary.common.listener.OnEditHintCallBack;
import com.xiaoyan.xylibrary.common.widget.toast.MyToast;

/**
 * 输入框的工具类
 */

public class EditUtil {


  /**
   * 获取edit集的字符串，若为null则弹出提示并返回为null，若不为null则返回全部输入的字符
   */
  public static String[] getEditsString(Context context, EditText... editTexts) {
    if (NullUtil.checkNull((Object[]) editTexts)) {
      return null;
    }
    String[] strings = new String[editTexts.length];
    for (int index = 0; index < editTexts.length; index++) {
      if (TextUtils.isEmpty(editTexts[index].getText().toString())) {
        MyToast.showToast(context, editTexts[index].getHint().toString());
        return null;
      }
      strings[index] = editTexts[index].getText().toString();
    }

    return strings;
  }

  /**
   * 获取edit集的字符串，若为null则弹出提示并返回为null，若不为null则返回全部输入的字符
   */
  public static String[] getEditsString(OnEditHintCallBack callBack, EditText... editTexts) {
    if (NullUtil.checkNull((Object[]) editTexts)) {
      return null;
    }
    String[] strings = new String[editTexts.length];
    for (int index = 0; index < editTexts.length; index++) {
      if (TextUtils.isEmpty(editTexts[index].getText().toString())) {
        if (callBack!=null){
          callBack.onHintTip(editTexts[index].getHint().toString());
        }
        return null;
      }
      strings[index] = editTexts[index].getText().toString();
    }

    return strings;
  }


  /**
   * 判断两个Edit里面的字符是否相同
   */
  public static boolean checkSameEdit(EditText editText1, EditText editText2) {
    if (NullUtil.checkNull(editText1, editText2)) {
      return false;
    }
    if (editText1.getText().toString().equals(editText2.getText().toString())) {
      return true;
    }
    return false;
  }
}
