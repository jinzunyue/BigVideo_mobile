package com.pbtd.mobile.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

public class MyStringRequest extends StringRequest {

	public MyStringRequest(String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(url, listener, errorListener);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		 String str = null;
	        try {
	            str = new String(response.data,"utf-8");
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
	}
}
