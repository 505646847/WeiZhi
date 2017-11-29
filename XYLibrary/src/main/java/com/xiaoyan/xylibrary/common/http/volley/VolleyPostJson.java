package com.xiaoyan.xylibrary.common.http.volley;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

/**
 * 使用Volley中的post操作传输数据（使用单例模式）
 *
 * @author xiejinxiong
 *
 */
public class VolleyPostJson {

	/** 数据请求队列对象 */
	private RequestQueue requestQueue;

	public VolleyPostJson(Context context) {

		requestQueue = Volley.newRequestQueue(context);
	}

	/**
	 * VolleyPost请求返回数据监听回调接口
	 *
	 * @author xiejinxiong
	 *
	 */
	public interface VolleyJsonRequestListener {

		/**
		 * 返回正确数据监听
		 *
		 * @param response
		 */
		public void getJsonResponse(JSONObject response);

		/**
		 * 返回错误数据监听
		 *
		 * @param error
		 */
		public void getJsonErrorResponse(VolleyError error);
	}

	/**
	 * 带参数的Volleypost请求
	 *
	 * @param url
	 *            (请求链接)
	 * @param map
	 *            (请求附带参数)
	 * @param volleyJsonRequestListener
	 *            (请求响应执行的方法)
	 */
	public void volleyPostResponse(String url, Map<String, Object> map,
								   final VolleyJsonRequestListener volleyJsonRequestListener) {

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, url, new JSONObject(map),
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						volleyJsonRequestListener.getJsonResponse(response);
					}
				}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				volleyJsonRequestListener.getJsonErrorResponse(error);
			}
		});

		requestQueue.add(jsonObjectRequest);
	}

	/**
	 * 不带参数的Volleypost请求
	 *
	 * @param url
	 *            (请求链接)
	 * @param volleyJsonRequestListener
	 *            (请求响应执行的方法)
	 */
	public void volleyPostResponse(String url,
								   final VolleyJsonRequestListener volleyJsonRequestListener) {

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				volleyJsonRequestListener.getJsonResponse(response);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				volleyJsonRequestListener.getJsonErrorResponse(error);
			}
		});

		requestQueue.add(jsonObjectRequest);
	}
}
