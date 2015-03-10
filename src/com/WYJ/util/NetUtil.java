package com.WYJ.util;

import com.WYJ.constant.MyConstant;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 网络操作工具类
 * @author lp
 *
 */
public class NetUtil {
	private static String SESSIONID=null;
	
	public static String postAndGetDaet(String url){
		String response=null;
		System.out.println(url);
		try{
			HttpPost httpPost=new HttpPost(url);
			httpPost.setHeader("Cookie", "JSESSIONID=" + SESSIONID);
			httpPost.setHeader("User-Agent", MyConstant.UESRAGENT_PHONE);
			DefaultHttpClient httpClient=new DefaultHttpClient();
			HttpResponse httpResponse=httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				response=EntityUtils.toString(httpResponse.getEntity());
			}
		}catch (Exception e) {
			System.out.println("error ");
			response="connect_error";
			e.printStackTrace();
		}
		return response;
	}

}
