package com.xiaoyan.xylibrary.framework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import butterknife.ButterKnife;
import com.xiaoyan.xylibrary.common.http.okhttp.HttpTool;
import com.xiaoyan.xylibrary.common.tools.ActivitiesManager;
import com.xiaoyan.xylibrary.common.widget.dialog.LoadingDialog;
import com.xiaoyan.xylibrary.common.widget.dialog.MySpecificDialog;
import com.xiaoyan.xylibrary.common.widget.dialog.MySpecificDialog.MyDialogCallBack;
import com.xiaoyan.xylibrary.common.widget.dialog.MySpecificDialog.MyDialogCallBackCenter;
import com.xiaoyan.xylibrary.common.widget.toast.MyToast;

/**
 * 最基础的Activity，每个Activity都需要继承
 *
 * @author xiejinxiong
 */
public abstract class BaseActivity extends AppCompatActivity {

  protected Activity act;
  protected HttpTool tool;
  private LoadingDialog myDialog;

  //便于在主线程中进行UI操作

  /**
   * 显示加载窗口
   */
  public void showLoading() {
    handler.sendEmptyMessage(0);
  }

  /**
   * 隐藏加载窗口
   */
  public void hiddenLoading() {
    handler.sendEmptyMessage(1);
  }

  protected Handler myHandler = new Handler();

  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 0:
          if (myDialog == null) {
            myDialog = new LoadingDialog(act, "", "loading");
          }
          myDialog.show();
          break;
        case 1:
          if (myDialog != null) {
            myDialog.dismiss();
          }
          break;
        case 2:
          myDialog.dismiss();
          finish();
          break;

        default:
          break;
      }
    }
  };

  /**
   * 返回布局ID
   */
  protected abstract View layoutView();

  /**
   * 初始化视图
   */
  protected abstract void InitView();

  /**
   * 控件的点击事件
   */
  public void ViewClick(View view) {
  }

  /**
   * 初始化视图之前设置
   */
  protected void InitSetting() {
  }

  /**
   * 初始化数据
   */
  protected void InitData(Bundle data) {
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    act = this;
    InitSetting();
    setContentView(layoutView());
    ActivitiesManager.getInstance().pushActivity(this);
    ButterKnife.bind(this);
    tool = HttpTool.getInstance(this);
    Bundle data = getIntent().getExtras();
    if (data != null) {
      InitData(data);
    }
    InitView();
  }

  protected View inflateLayout(int layoutResID) {
    return LayoutInflater.from(this).inflate(layoutResID, null);
  }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        AppMgr.getInstance().finishActivity(this);
//    }

//    /**
//     * 工具
//     */
////    @Override
//    public void showToast(String title) {
//        MyToast.showToast(act, title);
//    }

  public void BaseStartAct(Class<?> clazz) {
    Intent intent = new Intent();
    intent.setClass(act, clazz);
    startActivity(intent);
  }

  public void BaseStartAct(Class<?> clazz, Bundle data) {
    Intent intent = new Intent();
    intent.setClass(act, clazz);
    intent.putExtras(data);
    startActivity(intent);
  }

  public void BaseStartActForResult(Intent intent, int code) {
    startActivityForResult(intent, code);
  }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            onActResult(requestCode, data);
//
//            // 确保Activity下的Fragment也可以获取返回数据
//            FragmentManager fm = getSupportFragmentManager();
//            List<Fragment> fms = fm.getFragments();
//            for (int i = 0; i < fms.size(); i++) {
//                Fragment fgm = fms.get(i);
//                if (fgm != null) {
//                    handleResult(fgm, requestCode, resultCode, data);
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 递归调用，对所有子Fragement生效
//     */
//    private void handleResult(Fragment frag, int requestCode, int resultCode,
//        Intent data) {
//        frag.onActivityResult(requestCode, resultCode, data);
//        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
//        if (frags != null) {
//            for (Fragment f : frags) {
//                if (f != null) {
//                    handleResult(f, requestCode, resultCode, data);
//                }
//            }
//        }
//    }


  protected void onActResult(int requestCode, Intent data) {
  }

  /**
   * 返回上一个界面的Intent
   */
  public void setActResult(Intent intent) {
    //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
    setResult(RESULT_OK, intent);
    //此处一定要调用finish()方法
    finish();
  }

  /**
   * 运行权限返回
   */
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
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
    onPermissionsResult(requestCode, agree);
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

  }

  /**
   * 运行权限返回
   *
   * @param requestCode 请求标识
   * @param agree 是否允许
   */
  protected void onPermissionsResult(int requestCode, boolean agree) {
  }


  @Override
  protected void onDestroy() {
    // TODO Auto-generated method stub
    super.onDestroy();

    ActivitiesManager.getInstance().popOneActivity(this); // 销毁当前activity;
  }

  /**
   * 输出日志
   */
  protected void logE(String msg) {
    Log.e(getClass().getSimpleName(), msg);
  }

  /**
   * 封装具有两个按钮的特定dialog
   */
  public void showSpecificDialog(String strMeaasge, String strLeft, String strRight,
      MyDialogCallBack myDialogCallBack) {
    MySpecificDialog mySpecificDialog = new MySpecificDialog(BaseActivity.this);
    mySpecificDialog.showDialog(strMeaasge, strLeft, strRight, myDialogCallBack);
  }

  /**
   * 封装具有一个按钮的特定dialog
   */
  public void showSpecificDialog(String strMeaasge, String strCenter,
      MyDialogCallBackCenter myDialogCallBackCenter) {
    MySpecificDialog mySpecificDialog = new MySpecificDialog(BaseActivity.this);
    mySpecificDialog.showDialog(strMeaasge, strCenter, myDialogCallBackCenter);
  }

  /**
   * 弹出提示
   *
   * @param rString （String.xml文件中的字符串id）
   */
  public void showToast(int rString) {
    showToast(getResources().getString(rString));
  }

  /**
   * 弹出提示
   */
  public void showToast(final String text) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        MyToast.showToast(BaseActivity.this, text);
      }
    });
  }

  /**
   * 弹出提示
   *
   * @param rString （String.xml文件中的字符串id）
   * @param isLength_Long （是否为长提示）
   */
  public void showToast(final int rString, final boolean isLength_Long) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        showToast(getResources().getString(rString), isLength_Long);
      }
    });
  }

  /**
   * 弹出提示
   *
   * @param isLength_Long （是否为长提示）
   */
  public void showToast(String text, boolean isLength_Long) {
    MyToast.showToast(text, isLength_Long, BaseActivity.this);
  }

  /**
   * 简便输出System.out.println的值
   */
  public void outPut(Object obj) {
    System.out.println(obj.toString());
  }

  /**
   * 页面跳转
   */
  public void startActivity(Class<?> clz) {
    startActivity(new Intent(BaseActivity.this, clz));
  }

  /**
   * 携带数据的页面跳转
   */
  public void startActivity(Class<?> clz, Bundle bundle) {
    Intent intent = new Intent();
    intent.setClass(this, clz);
    if (bundle != null) {
      intent.putExtras(bundle);
    }
    startActivity(intent);
  }

//    /**
//     * 在UI线程中显示Toast
//     *
//     * @param msg
//     */
//    protected void showInUIThreadToast(final String msg) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                showToast(msg);
//            }
//        });
//    }

  /**
   * 便于使用findViewById
   */
  protected <T extends View> T findView(int id) {
    return (T) findViewById(id);
  }

  /**
   * 使输入框失去焦点
   */
  protected void hideSoftInput() {
    try {
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      if (imm.isActive()) {
        IBinder view = getCurrentFocus().getApplicationWindowToken();
        if (view != null) {
          imm.hideSoftInputFromWindow(view, 0);
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  /**
   * 隐藏软键盘
   */
  protected void hideSoftKeyboard() {
    if (getWindow().getAttributes().softInputMode
        != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
      if (getCurrentFocus() != null) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
            Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
      }
    }
  }


}
