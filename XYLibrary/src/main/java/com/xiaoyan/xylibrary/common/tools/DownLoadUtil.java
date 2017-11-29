package com.xiaoyan.xylibrary.common.tools;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.xiaoyan.xylibrary.common.listener.OnDownLoadImageCallBack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 下载工具类
 */

public class DownLoadUtil {

  /**
   * 下载图片
   */
  public static void downLoadImage(Context context, String strUrl, OnDownLoadImageCallBack callBack) {
    File file = null;
    File file1 = null;
    try {
      file = Glide.with(context)
          .load(strUrl)
          .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)  //Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL 加载图片的原始资源
          .get();
      //图片存储位置
      file1 = new File(context.getFilesDir(),System.currentTimeMillis()+"");
      copyFileUsingFileChannels(file,file1);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (callBack == null) {
        return;
      }
      if (file1 != null) {
        callBack.onSuccess(file1);
      } else {
        callBack.onError();
      }
    }
  }

  /**
   * 文件拷贝
   * @param source
   * @param dest
   * @throws IOException
   */
  private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
    FileChannel inputChannel = null;
    FileChannel outputChannel = null;
    try {
      inputChannel = new FileInputStream(source).getChannel();
      outputChannel = new FileOutputStream(dest).getChannel();
      outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
    } finally {
      inputChannel.close();
      outputChannel.close();
    }
  }
}
