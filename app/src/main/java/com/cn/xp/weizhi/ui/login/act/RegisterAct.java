package com.cn.xp.weizhi.ui.login.act;

import android.os.Bundle;
import android.view.View;
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

    @OnClick({R.id.tv_getVerificationCode,R.id.btn_register,R.id.btn_existingAccount})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_getVerificationCode:
                //获取验证码
                HttpGetCode();
                break;
            case R.id.btn_register:
                //注册操作
                Register();
                break;
            case R.id.btn_existingAccount:
                finish();
                break;
        }
    }

    private void Register() {
        String[] strEt = EditUtil.getEditsString(RegisterAct.this, etPhone, etPassword, etConfirmPassword,etVerificationCode);
        if (strEt==null){
            return;
        }
        String password1 = etPassword.getText().toString();
        String password2 = etConfirmPassword.getText().toString();
        if ( password1.length()<6 || password2.length()<6){
            showToast("密码少于8位数，请重新输入");
            return;
        }
        if(!password1.equals(password2)){
            showToast("您两次输入的密码不同，请重新输入");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile",strEt[0]);
        map.put("code",strEt[3]);
        map.put("password",strEt[1]);
        //联网操作
        tool.HttpLoad(getCode, CloudApi.register_url, map, resultListener);
    }

    /**
     * 获取验证码
     */
    private void HttpGetCode() {
//        String[] strEdit = EditUtil.getEditsString(RegisterAct.this, etPhone);
//        if (strEdit == null){
//            return;
//        }
//
//        Map<String, String> map = new HashMap<>();
//        map.put("mobile",strEdit[0]);
        //联网操作
//        tool.HttpLoad(getCode, CloudApi.register_url, map, resultListener);
        //联网操作
        new GetThread().start();
    }

    /**
     *HttpURLConnection get方式联网操作
     */
    class GetThread extends Thread{
        public void run(){
            HttpURLConnection conn=null;
            String urlStr=CloudApi.register_getcode_url+"?mobile="+etPhone.getText().toString()+"&type="+getCode;
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
                    if (code == 1){
                        showToast("成功获取验证码");
                        clickCode = true;
                        getCode();
                    } else {
                        //获取验证码失败
                        showToast(obj.optString("desc"));
                    }
                    break;
                case register:
                    if (code == 1){
                        showToast("desc："+obj.optString("desc"));
                        //保存账号
                        SharedAccount.getInstance(RegisterAct.this).save(etPhone.getText().toString(),"");
                        //解析并保存obj中data数据

                        //移除
//                        ActivitiesManager.getInstance().popAllActivity();
                        //EventBus用更新主页面账号
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
