package com.pbtd.mobile.volley;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

import java.util.Map;

public class VolleyController {
	
	private VolleyCallback mCallBack;
	
	private Context mContext;

	public static final String REQUEST_TAG = "volley_tag";
	
	public VolleyController(Context ctx){
		this.mContext = ctx;
	}
	
	public void requestGetAction(String url, VolleyCallback volleyCallback){
		innerRequestGet(url, REQUEST_TAG);
		mCallBack = volleyCallback;
	}

	public void requestGetAction(String url, String tag, VolleyCallback volleyCallback){
		innerRequestGet(url, tag);
		mCallBack = volleyCallback;
	}

	/**
	 * 进行POST请求
	 * @param url 请求Url
	 * @param bodyMap body体中参数
	 * @param headerMap	Header中参数
	 */
	public void requestPostAction(String url,Map<String, String> bodyMap,Map<String, String> headerMap){
		startRequestPost(url,bodyMap,headerMap);
	}
	
	private void innerRequestGet(String url, String tag) {
		ValueRequest requestGet = new ValueRequest(url, mResponseListener,mResponseErrorListener);
		requestGet.setTag(tag);
		VolleyUtils.getInstance(mContext.getApplicationContext()).addToRequestQueue(requestGet);
	}
	
	private void startRequestPost(String url,Map<String, String> bodyMap,Map<String, String> headerMap) {
		ValueRequest requestPost = new ValueRequest(Method.POST, url, bodyMap, headerMap, mResponseListener,mResponseErrorListener);
		requestPost.setTag("volley_tag");
		VolleyUtils.getInstance(mContext.getApplicationContext()).addToRequestQueue(requestPost);
	}

	public interface VolleyCallback{
		void onResponse(String response);
		void onErrorResponse(VolleyError error);
	}
	
	private Response.Listener<String> mResponseListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			if(mCallBack != null)
				mCallBack.onResponse(response);
		}
	};
	
	private ErrorListener mResponseErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			if(mCallBack != null)
				mCallBack.onErrorResponse(error);
		}
	};
	
}
