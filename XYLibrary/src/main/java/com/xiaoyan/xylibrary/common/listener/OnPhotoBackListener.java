package com.xiaoyan.xylibrary.common.listener;

import android.graphics.Bitmap;
import java.io.File;

/**
 * 图片返回数据
 */

public interface OnPhotoBackListener {

  void onSuccess(Bitmap bitmap, File file);
}
