package com.xiaoyan.xylibrary.common.tools;

import android.text.TextUtils;

/**
 * 判null工具类
 */

public class NullUtil {

  /**
   * 判断参数是否含有null，有则返回true，没有则返回false
   */
  public static boolean checkNull(Object... objects) {
    if (objects == null || objects.length == 0) {
      return true;
    }
    for (Object object : objects) {
      if (object == null) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断字符串是否为null
   * @param string
   * @return
   */
  public static String checkNull(String string){
    if (TextUtils.isEmpty(string)){
      return new String();
    }else {
      return string;
    }
  }
}
