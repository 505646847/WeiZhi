package com.xiaoyan.xylibrary.common.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 关于Bitmap工具类
 *
 * @author jinXiong.Xie
 */

public class BitmapUtil {

    public static void saveBitmapFile(Bitmap bitmap, File file) {
        //将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            //第一个参数图片的格式，第二个参数是图片质量，但是图片的高度和宽度是不会受影响的
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用RGB_565法进行bitmap压缩
     *
     * @param file
     * @return
     */
    public static Bitmap compressionBitmap(File file) {
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeFile(file.getPath(), options2);
    }

    /**
     * 固定尺寸压缩
     *
     * @param bitmap
     * @return
     */
    public static Bitmap createScaledBitmap(Bitmap bitmap, int size) {
        return Bitmap.createScaledBitmap(bitmap, size, size, true);
    }

    /**
     * 按比例压缩
     *
     * @param bitmap
     */
    public static Bitmap martixBitmap(Bitmap bitmap, int size) {

//    Log.e("wechat", "压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
//        + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        int max = height > width ? height : width;
        if (max <= size) {
            return bitmap;
        }
        float scale = size * 1.0f / max;
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
//    Log.e("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 )
//        + "Kb宽度为" + bm.getWidth() + "高度为" + bm.getHeight());
        return bm;
    }

    /**
     * 获取Bitmap
     *
     * @param file
     * @return
     */
    public static Bitmap getBitmap(File file) {
        return BitmapUtil.compressionBitmap(file);
    }

    /**
     * 获取Bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmap(String path) {
        return getBitmap(new File(path));
    }
}
