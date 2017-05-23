package com.pbtd.mobile.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.orhanobut.logger.Logger;
import com.pbtd.mobile.Constants;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ValueRequest extends StringRequest {

	private Map<String, String> maps;
	
	private Map<String, String> headers;

	private String mUrl;
	
	public ValueRequest(String url,Listener<String> listener,
			ErrorListener errorListener) {
		super(url, listener, errorListener);
		this.mUrl = url;
	}

	public ValueRequest(int method, String url,Map<String, String> valuses,Map<String, String> header,Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		this.maps = valuses;
		this.headers = header;
		this.mUrl = url;
	}
	
	/**设置body参数*/
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		if(this.maps == null)
			return super.getParams();
		else
			return maps;
	}
	
	/**设置Header头*/
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		if(this.headers != null)
			return headers;
		return super.getHeaders();
	}
	
	
	public interface RequestCallback {
		
		public void onResponse(String response);
		
		public void onErrorResponse(VolleyError error);
		
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		 String str = null;
	        try {
	            str = new String(response.data,"utf-8");
				Logger.i(Constants.LOGGER_TAG, "url:" + mUrl + "\n" + "time:" + System.currentTimeMillis());
				Logger.json(str);
	        } catch (UnsupportedEncodingException e) {
	            Logger.e(Constants.LOGGER_TAG, "url" + mUrl + "\n" + "errorMsg:" + e.getMessage());
	        }
	    return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
	}
	
	
	
}
