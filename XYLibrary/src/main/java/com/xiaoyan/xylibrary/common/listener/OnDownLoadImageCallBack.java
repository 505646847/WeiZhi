package com.xiaoyan.xylibrary.common.listener;

import java.io.File;

/**
 * 下载图片回调
 */

public interface OnDownLoadImageCallBack {

  void onSuccess(File file);

  void onError();
}
