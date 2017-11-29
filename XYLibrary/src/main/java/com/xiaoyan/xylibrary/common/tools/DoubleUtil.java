package com.xiaoyan.xylibrary.common.tools;

/**
 * Double功能
 */

public class DoubleUtil {

  public static String toFormatString(double d) {
    return toFormatString(d, "#.00");
  }

  /**
   * double保留两位小数
   */
  public static String toFormatString(String s) throws Exception {
    return toFormatString(Double.parseDouble(s));
  }


  public static String toFormatString(double d, String format) {
//    if (d==0){
//      return format.replace('#','0');
//    }else {
//      return (new java.text.DecimalFormat(format)).format(d);
//    }
    String str = (new java.text.DecimalFormat(format)).format(d);
    if (str.startsWith(".")) {
      str = "0" + str;
    }
    return str.replace("-.","-0.");
  }
}