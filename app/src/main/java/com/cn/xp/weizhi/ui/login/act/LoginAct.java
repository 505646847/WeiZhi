package com.cn.xp.weizhi.ui.login.act;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cn.xp.weizhi.R;
import com.cn.xp.weizhi.base.MyTitleBarActivity;
import com.cn.xp.weizhi.bean.UserData;
import com.cn.xp.weizhi.url.CloudApi;
import com.cn.xp.weizhi.utils.SharedAccount;
import com.xiaoyan.xylibrary.common.http.okhttp.HttpTool;
import com.xiaoyan.xylibrary.common.http.okhttp.ResultListener;
import com.xiaoyan.xylibrary.common.listener.OnGetCodeCallBack;
import com.xiaoyan.xylibrary.common.tools.EditUtil;
import com.xiaoyan.xylibrary.common.tools.GetCodeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class LoginAct extends MyTitleBarActivity {

    //1账号登录，2验证码登录
    private int loginSign = 1;

    private final int loginAccount = 1;
    private final int loginverificationCode = 2;

    private boolean clickCode = false;


    //联网工具
    private HttpTool tool;

    //获取验证码工具
    protected GetCodeUtil util;

    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_verification)
    TextView tvVerification;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_forgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.tv_verificationCode)
    TextView tvVerificationCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     tool = HttpTool.getInstance(this);
    }

    @Override
    protected int layoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initTitle() {
      setTitle("登录");
    }

    @Override
    protected void init() {
     util = new GetCodeUtil();
     initView();
    }


    private void initView() {
        //初始化默认值
        SettingView();
    }

    private void SettingView() {
        tvForgetPassword.setVisibility(View.VISIBLE);
        //设置tvAccount粗体字
        tvAccount.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        etPhone.setHintTextColor(ContextCompat.getColor(LoginAct.this,R.color.white));
        etPassword.setHintTextColor(ContextCompat.getColor(LoginAct.this,R.color.white));
    }

    @OnClick({R.id.tv_account,R.id.tv_verification,R.id.btn_login,R.id.btn_register,R.id.tv_verificationCode})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_account:
                //设置tv_account点击后布局
                TvAcountSetting();
                break;
            case R.id.tv_verification:
                //设置tv_verification点击后布局
                TvVerificationSetting();
                break;
            case R.id.tv_verificationCode:
                //获取验证码
                HttpGetCode();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                Intent intent = new Intent(LoginAct.this,RegisterAct.class);
                startActivity(intent);
                break;
        }
    }


    private void HttpGetCode() {
        String phone = etPhone.getText().toString();
        if (phone.length()<11){
            showToast("手机号码不正确");
            return;
        }
        new GetThread().start();
    }


    private void login(){
        String[] strEt = EditUtil.getEditsString(this, etPhone, etPassword);
        if (strEt == null){
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile",strEt[0]);
        map.put("password",strEt[1]);
        //联网操作
        if (loginSign == 1) {  //用户账号登录联网
            tool.HttpLoad(loginAccount, CloudApi.login_account_url, map, resultListener);
        }else if (loginSign == 2){//验证码登录联网
            tool.HttpLoad(loginverificationCode, CloudApi.login_verificationCode_url, map, resultListener);
        }
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
                case loginAccount:
                    if (code == 1){
                        showToast("登录成功");
                        showToast(obj.optString("desc"));
                        //保存到本地
                    } else {
                        //登录失败
                        showToast(obj.optString("desc"));
                    }
                    break;
                case loginverificationCode:
                    if (code == 1){
                        showToast("验证码登录成功");
                        showToast(obj.optString("desc"));
                        //保存phone,password到本地
                        SharedAccount.getInstance(LoginAct.this).save(etPhone.getText().toString(),etPassword.getText().toString());
                        //解析obj中的data数据，并保存
                    } else {
                        //登录失败
                        showToast(obj.optString("desc"));
                    }
                    break;
            }
        }

        @Override
        public void onOtherLogin() {

        }
    };

    /**
     *HttpURLConnection get方式联网操作
     */
    class GetThread extends Thread{
        public void run(){
            HttpURLConnection conn=null;
            String urlStr=CloudApi.register_getcode_url+"?mobile="+etPhone.getText().toString()+"&type="+loginverificationCode;
            InputStream is = null;
            String resultData = "";
            try{
                URL url = new URL(urlStr);
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET"); //使用get请求

                if(conn.getResponseCode() == 200){
                    is = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader bufferReader = new BufferedReader(isr);
                    String inputLine  = "";
                    while((inputLine = bufferReader.readLine()) != null){
                        resultData += inputLine + "\n";
                    }
                    showToast("get方法取回内容：" + resultData);
                    //解析resultData，并调用更新视图
                    analysisData(resultData);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    /**
     * 解析resultData，并调用更新视图
     * @param resultData
     * @throws JSONException
     */
    private void analysisData(String resultData) throws JSONException {
        JSONObject jSONObject = new JSONObject(resultData);
        String code = jSONObject.getString("code");
        if (code.equals("1")){
            showToast("获取验证码成功");
            clickCode = true;
            getCode();
        }else {
            //获取验证码失败
            showToast(jSONObject.getString("desc"));
        }
    }

    private void getCode() {
        util.getCode(60, new OnGetCodeCallBack() {
            @Override
            public void onCode(int num) {
                tvVerificationCode.setText(num + "s后可再次获取");
                tvVerificationCode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                tvVerificationCode.setText("获取验证码");
                tvVerificationCode.setEnabled(true);
            }
        });
    }



    private void TvVerificationSetting() {
        loginSign = 2;
        SpannableString s = new SpannableString("请输入验证码");
        etPassword.setHint(s);
        etPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
        //设置tvAccount默认常规字体
        tvAccount.setTypeface(Typeface.DEFAULT);
        //设置tvVerification粗体字
        tvVerification.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tvForgetPassword.setVisibility(View.INVISIBLE);
        tvVerificationCode.setVisibility(View.VISIBLE);
    }

    private void TvAcountSetting() {
        loginSign = 1;
        SpannableString s = new SpannableString("请输入密码");
        etPassword.setHint(s);
        etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
        //设置tvAccount粗体字
        tvAccount.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        //设置tvVerification常规字体
        tvVerification.setTypeface(Typeface.DEFAULT);
        tvForgetPassword.setVisibility(View.VISIBLE);
        tvVerificationCode.setVisibility(View.INVISIBLE);
    }

}
