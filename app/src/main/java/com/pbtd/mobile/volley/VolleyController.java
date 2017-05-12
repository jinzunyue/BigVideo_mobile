package com.pbtd.mobile.volley;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

import java.util.Map;

public class VolleyController {
	
	private VolleyCallback callback;
	
	private Context context;
	
	public VolleyController(Context ctx,VolleyCallback callback){
		this.context = ctx;
		this.callback = callback;
	}
	
	/**
	 * 进行get请求
	 * @param url 请求url
	 */ 
	public void requestGetAction(String url){
		startRequestGet(url);
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
	
	private void startRequestGet(String url) {
		ValueRequest requestGet = new ValueRequest(url, mResponseListener,mResponseErrorListener);
		requestGet.setTag("volley_tag");
		VolleyUtils.getInstance(context.getApplicationContext()).addToRequestQueue(requestGet);
	}
	
	private void startRequestPost(String url,Map<String, String> bodyMap,Map<String, String> headerMap) {
		ValueRequest requestPost = new ValueRequest(Method.POST, url, bodyMap, headerMap, mResponseListener,mResponseErrorListener);
		requestPost.setTag("volley_tag");
		VolleyUtils.getInstance(context.getApplicationContext()).addToRequestQueue(requestPost);
	}

	private ValueRequest.RequestCallback requestCallback = new ValueRequest.RequestCallback() {
		
		@Override
		public void onResponse(String response) {
			if(callback != null){
				callback.onResponse(response);
			}
				
		}
		
		@Override
		public void onErrorResponse(VolleyError error) {
			if(callback != null)
				callback.onErrorResponse(error);
				
		}
	};

	public interface VolleyCallback{
		
		public void onResponse(String response);
		
		public void onErrorResponse(VolleyError error);
		
		
	}
	
	private Response.Listener<String> mResponseListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			if(callback != null)
				callback.onResponse(response);
		}
	};
	
	private ErrorListener mResponseErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			if(callback != null)
				callback.onErrorResponse(error);
		}
	};
	
}
