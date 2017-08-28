package com.ruicheng.farmingmanageclient.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruicheng.farmingmanageclient.util.ServiceNameHandler;

public class TwitterRestClient {

//	public static final String BASE_URL = "http://192.168.1.113:8080/clmk";
	public static String BASE_URL = "http://"+ServiceNameHandler.BASE_URL_INTERFACE+"/clmk";
	
	
	public static AsyncHttpClient client = new AsyncHttpClient();

	public static String getAbsoluteUrl() {
		client.setTimeout(60*1000);
		return BASE_URL;
	}

	public static String getAbsoluteUrl(String relativeUrl) {
		client.setTimeout(60*1000);
		return BASE_URL + relativeUrl;
	}

	public static void get(RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(), params, responseHandler);
	}

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		System.out.println("http:url:"+url+":params:"+params+":responseHandler:"+responseHandler);
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(), params, responseHandler);
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}
}