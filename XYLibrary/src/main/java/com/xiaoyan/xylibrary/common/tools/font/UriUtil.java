package com.xiaoyan.xylibrary.common.tools.font;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Uri工具类
 * Created by xiaoYan on 2017/10/30 0030.
 */

public class UriUtil {

    public static Uri getUri(Context context, File file) {
        if (Build.VERSION.SDK_INT >= 24) {
            return FileProvider
                    .getUriForFile(context, context.getPackageName() + ".FileProvider",
                            file);
        } else {
            return Uri.fromFile(file);
        }
    }

    public static Uri getUri(Context context, String path) {
        return getUri(context, new File(path));
    }
}
