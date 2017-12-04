package com.cn.xp.weizhi.ui.login.act;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cn.xp.weizhi.R;
import com.cn.xp.weizhi.base.MyTitleBarActivity;
import com.cn.xp.weizhi.url.CloudApi;
import com.cn.xp.weizhi.utils.SharedAccount;
import com.xiaoyan.xylibrary.common.http.okhttp.HttpTool;
import com.xiaoyan.xylibrary.common.http.okhttp.ResultListener;
import com.xiaoyan.xylibrary.common.listener.OnGetCodeCallBack;
import com.xiaoyan.xylibrary.common.tools.ActivitiesManager;
import com.xiaoyan.xylibrary.common.tools.EditUtil;
import com.xiaoyan.xylibrary.common.tools.GetCodeUtil;
import com.xiaoyan.xylibrary.common.tools.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterAct extends MyTitleBarActivity {

    private final int getCode = 1;//获取验证码，1注册
    private final int register = 11;//注册的标识
    protected HttpTool tool;


    private boolean clickCode = false;//判断是否点击过获取验证码，false：未点击，true：已点击

    //获取验证码工具类
    private GetCodeUtil util;

    @BindView(R.id.et_Phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.et_verificationCode)
    EditText etVerificationCode;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.btn_existingAccount)
    Button btnExistingAccount;
    @BindView(R.id.btn_agreement)
    Button btnAgreement;
    @BindView(R.id.tv_getVerificationCode)
    TextView tvGetVerificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
        tool = HttpTool.getInstance(this);
        initStatus();
    }

    @Override
    protected int layoutResID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initTitle() {
    setTitle("注册");
    }

    @Override
    protected void init() {
     util = new GetCodeUtil();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initStatus() {
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorblue));
    }

    @OnClick({R.id.tv_getVerificationCode,R.id.btn_register,R.id.btn_existingAccount})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_getVerificationCode:
                //获取验证码
                httpGetCode();
                break;
            case R.id.btn_register:
                //注册操作
                register();
                break;
            case R.id.btn_existingAccount:
                finish();
                break;
        }
    }

    private void register() {
        String[] strEt = EditUtil.getEditsString(RegisterAct.this, etPhone, etPassword, etConfirmPassword,etVerificationCode);
        if (strEt==null) {
            return;
        }
        String password1 = etPassword.getText().toString();
        String password2 = etConfirmPassword.getText().toString();
        if ( password1.length() < 6 || password2.length() < 6) {
            showToast("密码少于8位数，请重新输入");
            return;
        }
        if (!password1.equals(password2)) {
            showToast("您两次输入的密码不同，请重新输入");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile",strEt[0]);
        map.put("code",strEt[3]);
        map.put("password",strEt[1]);
        //联网操作
        tool.HttpLoad(register, CloudApi.register_url, map, resultListener);
    }

    /**
     * 获取验证码
     */
    private void httpGetCode() {
        String[] strEdit = EditUtil.getEditsString(RegisterAct.this, etPhone);
        if (strEdit == null) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("mobile",strEdit[0]);
//        map.put("type",1);
//        联网操作
        tool.HttpLoad(getCode, CloudApi.register_getcode_url, map, resultListener);
//        联网操作
//        new GetThread().start();
    }

    ResultListener resultListener = new ResultListener(){

        @Override
        public void state(int id, boolean isStartOrEnd) {
        if (isStartOrEnd){
            showLoading();
        } else {
            hiddenLoading();
        }
        }

        @Override
        public void fail(int id, Call call, Exception e) {
            showToast("服务器出现异常，请稍后再试");
        }

        @Override
        public void success(int id, Call call, Response response, JSONObject obj) {
            int code = obj.optInt("code");
            switch (id){
                case getCode:
                    if (code == 1) {
                        showToast("获取验证码成功");
//                        showToast(obj.optString("desc"));
                        clickCode = true;
                        getCode();
                    } else {
                        //获取验证码失败
                        showToast(obj.optString("desc"));
                    }
                    break;
                case register:
                    if (code == 1) {
                        showToast(obj.optString("desc"));
                        //保存账号
                        SharedAccount.getInstance(RegisterAct.this).save(etPhone.getText().toString(),"");
                        //移除
                        ActivitiesManager.getInstance().popActivity();
                    }else if (code == 0) {
                        //用户已注册
                        showToast(obj.optString("desc"));
                    }
                    break;

            }
        }

        @Override
        public void onOtherLogin() {

        }
    };

    private void getCode() {
        util.getCode(60, new OnGetCodeCallBack() {
            @Override
            public void onCode(int num) {
                tvGetVerificationCode.setText(num + "s后可再次获取");
                tvGetVerificationCode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                tvGetVerificationCode.setText("获取验证码");
                tvGetVerificationCode.setEnabled(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (util!=null){
            util.closeGetCode();
        }
        super.onDestroy();
    }

}
