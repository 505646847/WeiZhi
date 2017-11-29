package com.xiaoyan.xylibrary.common.http.okhttp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.xiaoyan.xylibrary.common.tools.DebugUtil;
import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/6/9.
 */

public class HttpTool {

    private static final String TAG = "HttpTool";

    public final static int CONNECT_TIMEOUT = 180;
    public final static int READ_TIMEOUT = 180;
    public final static int WRITE_TIMEOUT = 180;

    private static HttpTool instance;
    private static OkHttpClient okHttpClient;
    private static Context mContext;

    public static HttpTool getInstance(Context context) {
        if (instance == null) {
            instance = new HttpTool(context);
            mContext = context;
        }
        return instance;
    }

    private HttpTool(Context context) {
        //添加Cookie持久化
        ClearableCookieJar cookieJar =
            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        okHttpClient = new OkHttpClient.Builder()
            .cookieJar(cookieJar)
//            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
//            .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
//            .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
            .build();

    }

    /**
     * 只有参数的Post请求
     *
     * @param id 请求id
     * @param url 请求的url
     * @param map 请求的参数
     * @param listener 请求返回
     */
    public void HttpLoad(final int id, final String url, Map<String, String> map,
        final ResultListener listener) {
        DebugUtil.error("Request-url:" + url + ",map:" + map.toString());
        listener.state(id, true);
        FormBody.Builder formBody = new FormBody.Builder();
        for (String key : map.keySet()) {
            String value = map.get(key);
            formBody.add(key, value);
        }
        FormBody body = formBody.build();
        Request request = new Request.Builder()
            .post(body)
            .url(url)
            .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.state(id, false);
                DebugUtil.error("服务器连接异常");
                listener.fail(id, call, e);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                listener.state(id, false);
                String s = response.body().string();
                DebugUtil.error("onResponse: " + url + ":" + s);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(s);
                } catch (JSONException e) {
                    listener.fail(id, call, e);
                    e.printStackTrace();
                }
                if (obj == null) {
                    obj = new JSONObject();
                    try {
                        obj.put("code", 0);
                        obj.put("desc", "服务器异常");
                        obj.put("data", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (obj.optInt("code") == 2 ){
                    if (mContext != null) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onOtherLogin();
                            }
                        });
                    } else {
                        Log.e(TAG, "onResponse: mContext == null");
                    }
                } else {
                    final JSONObject finalObj = obj;
                    if (mContext != null) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.success(id, call, response, finalObj);
                            }
                        });
                    } else {
                        Log.e(TAG, "onResponse: mContext == null");
                    }
                }
            }
        });
    }

    /**
     * 文件和参数混合请求
     *
     * @param id 请求id
     * @param url 请求的url
     * @param map 请求的参数
     * @param fileMap 请求的文件
     * @param listener 请求返回
     */
    public void HttpLoadFiles(final int id, final String url, Map<String, String> map,
        Map<String, File> fileMap, final ResultListener listener) {
        DebugUtil
            .error("Request-url:" + url + ",map:" + map.toString() + ",fileMap:" + fileMap.toString());
        listener.state(id, true);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加文件
        if (fileMap != null) {
            for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                String key = entry.getKey();
                File value = entry.getValue();
                RequestBody fileBody = RequestBody.create(
                    MediaType.parse(getMediaType(value.getName())), value);
                builder.addFormDataPart(key, value.getName(), fileBody);

            }
        }
        //添加参数
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.state(id, false);
                DebugUtil.error("服务器连接异常");
                listener.fail(id, call, e);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                listener.state(id, false);
                String s = response.body().string();
                DebugUtil.error("onResponse: " + url + ":" + s);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(s);
                } catch (JSONException e) {
                    listener.fail(id, call, e);
                    e.printStackTrace();
                }
                if (obj == null) {
                    obj = new JSONObject();
                    try {
                        obj.put("code", 0);
                        obj.put("desc", "服务器异常");
                        obj.put("data", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (obj.optInt("code") == 2) {
                    if (mContext != null) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onOtherLogin();
                            }
                        });
                    } else {
                        Log.e(TAG, "onResponse: mContext == null");
                    }

                } else {
                    final JSONObject finalObj = obj;
                    if (mContext != null) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.success(id, call, response, finalObj);
                            }
                        });
                    } else {
                        Log.e(TAG, "onResponse: mContext == null");
                    }

                }
            }
        });
    }

    /**
     * 根据文件的名称判断文件的Mine值
     */
    private String getMediaType(String fileName) {
        FileNameMap map = URLConnection.getFileNameMap();
        String contentTypeFor = map.getContentTypeFor(fileName);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
