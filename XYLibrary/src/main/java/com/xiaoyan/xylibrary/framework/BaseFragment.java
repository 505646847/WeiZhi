package com.xiaoyan.xylibrary.framework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.xiaoyan.xylibrary.common.http.okhttp.HttpTool;
import com.xiaoyan.xylibrary.common.widget.dialog.LoadingDialog;
import com.xiaoyan.xylibrary.common.widget.toast.MyToast;

/**
 * 最基础的Fragment，每个Fragment都需要继承
 *
 * @author xiejinxiong
 */
public abstract class BaseFragment extends Fragment {

  protected Activity act;
  protected HttpTool tool;

  /**
   * 返回布局ID
   */
  protected abstract View layoutView();

  /**
   * 初始化视图
   */
  protected abstract void InitView(View view);


  /**
   * 是否对用户可见
   */
  protected boolean mIsVisible;
  /**
   * 是否加载完成
   * 当执行完oncreatview,View的初始化方法后方法后即为true
   */
  protected boolean mIsPrepare;

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);

    this.mIsVisible = isVisibleToUser;

    if (isVisibleToUser) {
      onVisibleToUser();
    }
  }

  /**
   * 用户可见时执行的操作
   *
   * @author 漆可
   * @date 2016-5-26 下午4:09:39
   */
  protected void onVisibleToUser() {
    if (mIsPrepare && mIsVisible) {
      onLazyLoad();
    }
  }

  /**
   * 懒加载，仅当用户可见切view初始化结束后才会执行
   *
   * @author 漆可
   * @date 2016-5-26 下午4:10:20
   */
  protected void onLazyLoad() {
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    act = (BaseActivity) context;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(layoutId(), container, false);
    View view = layoutView();
    ButterKnife.bind(view);
    tool = HttpTool.getInstance(getActivity());
    Bundle data = getArguments();
    if (data != null) {
      InitData(data);
    }
    InitView(view);
    mIsPrepare = true;
    return view;
  }

  protected View inflateLayout(int layoutResID) {
    return LayoutInflater.from(getActivity()).inflate(layoutResID, null);
  }

  /**
   * 初始化参数
   */
  protected void InitData(Bundle view) {
  }

  /**
   * 工具
   */
  public void showToast(final String title) {
    if (getActivity() == null) {
      return;
    }
    getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        MyToast.showToast(getActivity(), title);
      }
    });
  }

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
    onActResult(code, intent);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK) {
      onActResult(requestCode, data);
    }
  }

  protected void onActResult(int requestCode, Intent data) {
  }

  /**
   * 返回上一个界面的Intent
   */
  public void setActResult(Intent intent) {
    //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
    act.setResult(act.RESULT_OK, intent);
    //此处一定要调用finish()方法
    act.finish();
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

  private LoadingDialog myDialog;
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
          break;

        default:
          break;
      }
    }
  };
}
