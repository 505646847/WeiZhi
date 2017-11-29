package com.xiaoyan.xylibrary.common.http.okhttp;

import okhttp3.Call;
import okhttp3.Response;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/6/9.
 */

public interface ResultListener {
    void state(int id, boolean isStartOrEnd);
    void fail(int id, Call call, Exception e);
    void success(int id, Call call, Response response, JSONObject obj);
    void onOtherLogin();
}
