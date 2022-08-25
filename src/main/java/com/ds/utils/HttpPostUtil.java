package com.ds.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpPostUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpPostUtil.class);

	private HttpPostUtil() {
		super();
	}

	/**
	 * HttpClient
	 * @param url
	 * @param json
	 * @return
	 */
	public static String post(String url,String json){
		HttpClient client= new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		request.setHeader("Content-Type","application/json; charset=utf-8");
		try {
			request.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
			HttpResponse resp = client.execute(request);
			HttpEntity entity = resp.getEntity();
			StringBuilder sb = new StringBuilder("");
			if(entity!=null){
				//解析返回数据
				// 读取响应
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
				String lines;

				while ((lines = reader.readLine()) != null) {
					lines = new String(lines.getBytes(), "utf-8");
					sb.append(lines);
				}
				reader.close();
				// 断开连接
				request.clone();
				//logger.info("网络请求结果，地址：{}，返回：{} ", url, sb);
				return sb.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * HttpURLConnection
	 * @param path
	 * @param params
	 * @return
	 */
	public static String post(String path, Object params) {
		try {
			String content = null;
			if (params != null) {
				content = JSON.toJSONString(params);
			}
			// 创建连接
			URL url = new URL(path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();

			// POST请求
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(content);
			out.flush();
			out.close();

			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuilder sb = new StringBuilder("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			reader.close();
			// 断开连接
			connection.disconnect();
			//logger.info("网络请求结果，地址：{}，返回：{} ", path, sb);
			return sb.toString();
		} catch (Exception e) {
			logger.info("网络请求异常,地址：{}，返回：{}", path, e.getMessage());
			return e.getMessage();
		}
	}

}
