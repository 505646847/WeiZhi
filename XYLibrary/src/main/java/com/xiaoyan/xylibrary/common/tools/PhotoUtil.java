package com.xiaoyan.xylibrary.common.tools;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.werb.permissionschecker.PermissionChecker;
import com.xiaoyan.xylibrary.common.listener.OnPhotoBackListener;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * 图片处理工具类
 *
 * @author jinXiong.Xie
 */

public class PhotoUtil {

  public static final int TAKE_CAMERA = 1;//拍照
  public static final int CUT_CAMERA = 2;//拍照后截取
  public static final int CUT_PHOTO = 3;//选择图片后截取
  public static final int CHOOSE_PHOTO = 4;//选择图片

  private final int choosePhoto = 1;//选择本地图片
  private final int takeCamera = 2;//拍照
  private int type = -1;//记录当前状态，拍照，或者选择本地图片
  private boolean isCut = false;//判断是否剪切

  static final String[] PERMISSIONS_SELECT_PHOTO = new String[]{//选择图片所需要的权限
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
  };
  static final String[] PERMISSIONS_TAKE_CAMERA = new String[]{//拍照所需要的权限
      Manifest.permission.CAMERA,
  };

  private Activity activity;
  private PermissionChecker permissionChecker;

  private Uri imageUri;
  private File outputImage;

  public PhotoUtil(Context context) {
    this.activity = (Activity) context;
    permissionChecker = new PermissionChecker(activity); // initialize，must need

  }

  public Uri getImageUri() {
    return imageUri;
  }

  public File getOutputImage() {
    return outputImage;
  }

  /**
   * 选择本地图片
   */
  public void choosePhoto(boolean isCut) {
    type = choosePhoto;
    this.isCut = isCut;
    if (permissionChecker.isLackPermissions(PERMISSIONS_SELECT_PHOTO)) {
      Toast.makeText(activity, "读取手机相册需要获取存储权限", Toast.LENGTH_SHORT).show();
      permissionChecker.requestPermissions();

    } else {
      openAlbum();
    }
  }

  /**
   * 拍照
   */
  public void takeCamera(boolean isCut) {
    type = takeCamera;
    this.isCut = isCut;
    if (permissionChecker.isLackPermissions(PERMISSIONS_TAKE_CAMERA)) {
      permissionChecker.requestPermissions();
    } else {
      openCamera();
    }
  }

  private void openCamera() {
    //创建File对象，用于存储拍照后的图片
    outputImage = new File(activity.getExternalCacheDir(), "output_image.jpg");
    try {
      if (outputImage.exists()) {
        outputImage.delete();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (VERSION.SDK_INT >= 24) {
      imageUri = FileProvider
          .getUriForFile(activity, activity.getPackageName() + ".FileProvider",
              outputImage);
    } else {
      imageUri = Uri.fromFile(outputImage);
    }
    //启动照相机程序
    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    activity.startActivityForResult(intent, TAKE_CAMERA);
  }

  private void openAlbum() {
    //启动选择图片
    Intent intent = new Intent("android.intent.action.GET_CONTENT");
    intent.setType("image/*");
    activity.startActivityForResult(intent, CHOOSE_PHOTO);
  }

  private void showDialog() {
    if (permissionChecker != null) {
      permissionChecker.showDialog();
    }
  }

  private void handleImage(Intent data, OnPhotoBackListener listener) {
    if (VERSION.SDK_INT >= 19) {
      handleImageOnKitKat(data, listener);
    } else {
      handleImageBeforeKitKat(data, listener);
    }
  }


  @TargetApi(19)
  private void handleImageOnKitKat(Intent data, OnPhotoBackListener listener) {
    String imagePath = null;
    Uri uri = data.getData();
    if (DocumentsContract.isDocumentUri(activity, uri)) {
      //如果是document类型的uri，则通过document id 处理
      String docId = DocumentsContract.getDocumentId(uri);
      if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
        String id = docId.split(":")[1];//解析出数字格式的id
        String selection = Media._ID + "=" + id;
        imagePath = getImagePath(Media.EXTERNAL_CONTENT_URI, selection);
      } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
        Uri contentUri = ContentUris
            .withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
        imagePath = getImagePath(contentUri, null);
      }
    } else if ("content".equalsIgnoreCase(uri.getScheme())) {
      //如果content类型的uri，则使用普通方式处理
      imagePath = getImagePath(uri, null);
    } else if ("file".equalsIgnoreCase(uri.getScheme())) {
      //如果是file类型的uri，直接获取图片路径即可
      imagePath = uri.getPath();
    }
    displayImage(imagePath, listener);
  }


  private String getImagePath(Uri uri, String selection) {
    String path = null;
    //通过Uri和selection来获取真实的图片路径
    Cursor cursor = activity.getContentResolver().query(uri, null, selection, null, null);
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        path = cursor.getString(cursor.getColumnIndex(Media.DATA));
      }
      cursor.close();
    }
    return path;
  }

  private void handleImageBeforeKitKat(Intent data, OnPhotoBackListener listener) {
    Uri uri = data.getData();
    String imagePath = getImagePath(uri, null);
    displayImage(imagePath, listener);
  }

  private void displayImage(String imagePath, OnPhotoBackListener listener) {
    if (imagePath != null) {
      if (isCut) {
        cutPhoto(imagePath);
      } else {
        if (listener != null) {
          listener.onSuccess(BitmapFactory.decodeFile(imagePath), new File(imagePath));
        }
      }
    } else {
      Toast.makeText(activity, "failed to get image", Toast.LENGTH_SHORT).show();
    }
  }

  private void cutPhoto(String imagePath) {
    //      Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//      imgPhoto.setImageBitmap(bitmap);
    File inputImage = new File(imagePath);
//      Bitmap bm = BitmapUtil.compressionBitmap(outputImage);
//      BitmapUtil.saveBitmapFile(BitmapUtil.martixBitmap(bm), outputImage);
    //创建File对象，用于存储拍照后的图片
    outputImage = new File(activity.getExternalCacheDir(), "output_image.jpg");
    try {
      if (outputImage.exists()) {
        outputImage.delete();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    Uri inImageUri;
    if (VERSION.SDK_INT >= 24) {
      inImageUri = FileProvider
          .getUriForFile(activity, activity.getPackageName() + ".FileProvider",
              inputImage);
      imageUri = FileProvider
          .getUriForFile(activity, activity.getPackageName() + ".FileProvider",
              outputImage);
    } else {
      inImageUri = Uri.fromFile(inputImage);
      imageUri = Uri.fromFile(outputImage);
    }

    Crop.of(inImageUri, imageUri).asSquare().start(activity, CUT_PHOTO);
    Toast.makeText(activity, "请拖动蓝框直线进行缩放", Toast.LENGTH_SHORT).show();

  }

  /**
   * 压缩图片（只能压缩得到拍照和剪切后的Bitmap）
   */
  private Bitmap compressionBitMap(int size) {
    Bitmap bm = BitmapUtil.compressionBitmap(outputImage);
    BitmapUtil.saveBitmapFile(BitmapUtil.martixBitmap(bm, size), outputImage);

    return BitmapFactory.decodeFile(outputImage.getPath());
  }

  /**
   * 运行权限检查
   */
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults, OnShowDialogListener dialogListener) {
//    boolean agree = grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    boolean agree = true;
    if (grantResults != null && grantResults.length > 0) {
      for (int i = 0; i < grantResults.length; i++) {
        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
          agree = false;
          break;
        }
      }
    }
    switch (requestCode) {
      case PermissionChecker.PERMISSION_REQUEST_CODE:
        if (agree) {
          switch (type) {//根据开始的不同选择调用不同方式
            case choosePhoto:
              openAlbum();
              break;
            case takeCamera:
              openCamera();
              break;
          }
        } else {
          if (dialogListener==null){
            showDialog();
          }else {
            dialogListener.showDialog();
          }
        }
        break;
    }
  }

  public interface OnShowDialogListener{
    void showDialog();
  }
  /**
   * 图片处理结果
   */
  public void onActivityResult(int requestCode, int resultCode, Intent data,
      OnPhotoBackListener listener) {
    switch (requestCode) {
      case TAKE_CAMERA:
        if (resultCode == RESULT_OK) {
          if (isCut) {
            Crop.of(imageUri, imageUri).asSquare().start(activity, CUT_CAMERA);
            Toast.makeText(activity, "请拖动蓝框直线进行缩放", Toast.LENGTH_SHORT).show();
          } else {
            if (listener != null) {
              listener.onSuccess(getBitmap(), outputImage);
            }
          }
        }
        break;
      case CUT_CAMERA:
      case CUT_PHOTO:
        if (resultCode == RESULT_OK) {
          //将拍摄的照片显示出来
//            Bitmap bitmap = BitmapFactory
//                .decodeStream(getContentResolver().openInputStream(imageUri));

          if (listener != null) {
            listener.onSuccess(getBitmap(), outputImage);
          }
        }

        break;
      case CHOOSE_PHOTO:
        if (resultCode == RESULT_OK) {
          handleImage(data, listener);
        }
        break;
      default:
        break;
    }
  }

  /**
   * 获取图片（只能获取得到拍照和剪切后的Bitmap）
   */
  private Bitmap getBitmap() {
    if (outputImage != null) {
      return BitmapFactory.decodeFile(outputImage.getPath());
    }
    return null;
  }

}
