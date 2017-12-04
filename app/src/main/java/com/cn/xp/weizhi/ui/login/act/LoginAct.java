package com.cn.xp.weizhi.ui.login.act;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.SpannableString;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.xp.weizhi.MainActivity;
import com.cn.xp.weizhi.R;
import com.cn.xp.weizhi.bean.UserBean;
import com.cn.xp.weizhi.bean.UserData;
import com.cn.xp.weizhi.url.CloudApi;
import com.cn.xp.weizhi.utils.SharedAccount;
import com.xiaoyan.xylibrary.common.http.okhttp.HttpTool;
import com.xiaoyan.xylibrary.common.http.okhttp.ResultListener;
import com.xiaoyan.xylibrary.common.listener.OnGetCodeCallBack;
import com.xiaoyan.xylibrary.common.tools.EditUtil;
import com.xiaoyan.xylibrary.common.tools.GetCodeUtil;
import com.xiaoyan.xylibrary.common.tools.LogUtil;

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
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class LoginAct extends Activity {

    //1账号登录，2验证码登录
    private int loginSign = 1;

    private final int loginAccount = 1;
    private final int loginverificationCode = 2;
    private final int getverificationCode = 3;

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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        tool = HttpTool.getInstance(this);
        util = new GetCodeUtil();
        initView();
    }



    private void initView() {
        //初始化默认值
        settingView();
    }

    private void settingView() {
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
                tvAcountSetting();
                break;
            case R.id.tv_verification:
                //设置tv_verification点击后布局
                tvVerificationSetting();
                break;
            case R.id.tv_verificationCode:
                //获取验证码
                httpGetCode();
                break;
            case R.id.btn_login:
                //登录
//                login();
                startActivity(new Intent(LoginAct.this, MainActivity.class));
                break;
            case R.id.btn_register:
                Intent intent = new Intent(LoginAct.this,RegisterAct.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void httpGetCode() {
        String phone = etPhone.getText().toString();
        if (phone.length() < 11) {
//            showToast("手机号码不正确");
            Toast.makeText(LoginAct.this,"手机号码不正确",Toast.LENGTH_SHORT);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("type","2");
        tool.HttpLoad(getverificationCode, CloudApi.register_getcode_url, map, resultListener);
    }


    private void login(){
        String[] strEt = EditUtil.getEditsString(this, etPhone, etPassword);
        if (strEt == null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile",strEt[0]);
        map.put("password",strEt[1]);
        //联网操作
        if (loginSign == 1) {  //用户账号登录联网
            tool.HttpLoad(loginAccount, CloudApi.login_account_url, map, resultListener);
        } else if (loginSign == 2) {//验证码登录联网
            tool.HttpLoad(loginverificationCode, CloudApi.login_verificationCode_url, map, resultListener);
        }
    }


    ResultListener resultListener = new ResultListener(){

        @Override
        public void state(int id, boolean isStartOrEnd) {
            if (isStartOrEnd) {
//                showLoading();
            } else {
//                hiddenLoading();
            }
        }

        @Override
        public void fail(int id, Call call, Exception e) {
//            showToast("服务器出现异常，请稍后再试");
            Toast.makeText(LoginAct.this,"服务器出现异常，请稍后再试",Toast.LENGTH_SHORT);
        }

        @Override
        public void success(int id, Call call, Response response, JSONObject obj) {
            int code = obj.optInt("code");
            switch (id){
                case loginAccount:
                    if (code == 1){
//                        showToast("登录成功");
                        Toast.makeText(LoginAct.this,"登录成功",Toast.LENGTH_SHORT);
                        Toast.makeText(LoginAct.this,obj.optString("desc"),Toast.LENGTH_SHORT);
//                        showToast(obj.optString("desc"));
                        //保存phone,password到本地
                        SharedAccount.getInstance(LoginAct.this).save(etPhone.getText().toString(),etPassword.getText().toString());
                        //保存到本地
                        saveLogin(obj);
                        startActivity(new Intent(LoginAct.this, MainActivity.class));
                    } else {
                        //登录失败
//                        showToast(obj.optString("desc"));
                        Toast.makeText(LoginAct.this,obj.optString("desc"),Toast.LENGTH_SHORT);

                    }
                    break;
                case loginverificationCode:
                    if (code == 1){
//                        showToast("验证码登录成功");
                        Toast.makeText(LoginAct.this,"验证码登录成功",Toast.LENGTH_SHORT);
//                        showToast(obj.optString("desc"));
                        Toast.makeText(LoginAct.this,obj.optString("desc"),Toast.LENGTH_SHORT);

                    } else {
                        //登录失败
//                        showToast(obj.optString("desc"));
                        Toast.makeText(LoginAct.this,obj.optString("desc"),Toast.LENGTH_SHORT);
                    }
                    break;
                case getverificationCode:
                    if (code == 1){
                        //获取验证码成功
//                        showToast("获取验证码成功");
                        Toast.makeText(LoginAct.this,"获取验证码成功",Toast.LENGTH_SHORT);
//                        showToast(obj.optString("desc"));
                        getCode();
                    }else {
//                        showToast("获取验证码失败");
                        Toast.makeText(LoginAct.this,"获取验证码失败",Toast.LENGTH_SHORT);
//                        showToast(obj.optString("desc"));
                        Toast.makeText(LoginAct.this,obj.optString("desc"),Toast.LENGTH_SHORT);
                    }
                    break;
            }
        }

        @Override
        public void onOtherLogin() {

        }
    };

    //解析Json
    private void saveLogin(JSONObject obj) {
        int code = obj.optInt("code");
        UserBean userBean = new UserBean();
        userBean.setCode(code);
        String desc = obj.optString("desc");
        userBean.setDesc(desc);
        try {
            JSONObject dataBeanResult = obj.getJSONObject("data");
            String ryToken = dataBeanResult.getString("ryToken");
            String mobile = dataBeanResult.getString("mobile");
            String sessionId = dataBeanResult.getString("sessionId");
            int type = dataBeanResult.getInt("type");
            int fz = dataBeanResult.getInt("fz");
            String createTime = dataBeanResult.getString("createTime");
            String loginIp = dataBeanResult.getString("loginIp");
            int id = dataBeanResult.getInt("id");
            int isRealName = dataBeanResult.getInt("isRealName");
            String email = dataBeanResult.getString("email");
            String account = dataBeanResult.getString("account");
            Boolean isComplete = dataBeanResult.getBoolean("isComplete");

            UserBean.DataBean dataBean = new UserBean.DataBean();
            dataBean.setRyToken(ryToken);
            dataBean.setMobile(mobile);
            dataBean.setSessionId(sessionId);
            dataBean.setType(type);
            dataBean.setFz(fz);
            dataBean.setCreateTime(createTime);
            dataBean.setLoginIp(loginIp);
            dataBean.setId(id);
            dataBean.setIsRealName(isRealName);
            dataBean.setEmail(email);
            dataBean.setAccount(account);
            dataBean.setComplete(isComplete);

            userBean.setData(dataBean);
        } catch (JSONException e) {
            e.printStackTrace();
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



    private void tvVerificationSetting() {
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

    private void tvAcountSetting() {
        loginSign = 1;
        SpannableString s = new SpannableString("请输入密码");
        etPassword.setHint(s);
        etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //设置tvAccount粗体字
        tvAccount.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        //设置tvVerification常规字体
        tvVerification.setTypeface(Typeface.DEFAULT);
        tvForgetPassword.setVisibility(View.VISIBLE);
        tvVerificationCode.setVisibility(View.INVISIBLE);
    }

}
