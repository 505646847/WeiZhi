package com.xiaoyan.xylibrary.common.tools;

/**
 * 进度工具类
 */

public class ProgressUtil {

  /**
   * 获取当前进度
   * @param now
   * @param max
   * @return
   */
  public static int getProgress(double now, double max){
    return (int) (now/max*100);
  }
}
