package com.xiaoyan.xylibrary.common.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Intent工具类
 */

public class IntentUtil {

  /**
   * 播放视频
   */
  public static void showVideo(Context context, String cameraPath) {
    if (TextUtils.isEmpty(cameraPath)) {
      return;
    }
    //  0.    定义好视频的路径
    Uri uri = Uri.parse(cameraPath);

    //  1.  先设定好Intent
    Intent intent = new Intent(Intent.ACTION_VIEW);

    //  2.  设置好 Data：播放源，是一个URI
    //      设置好 Data的Type：类型是 “video/mp4"
    intent.setDataAndType(uri, "video/*");

    //  3.  跳转：
    context.startActivity(intent);

  }

  /**
   * 创建打开浏览器Intent
   */
  public static void intentToBrowser(Context ctx, String url) {
    Intent intent = new Intent("android.intent.action.VIEW",Uri.parse(url));
    ctx.startActivity(intent);
  }
}
