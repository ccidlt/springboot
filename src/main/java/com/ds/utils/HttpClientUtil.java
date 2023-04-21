package com.ds.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    /**
     * httpclient post
     * @param url
     * @param params
     * @return
     */
    public static String sendPost(String url, Map<String, String> params) {
        try {
            String body = null;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            HttpEntity entity = new UrlEncodedFormEntity(nvps, "UTF-8");
            httpPost.setEntity(entity);
            //httpClient配置
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(60000)		//设置连接超时时间
                    .setSocketTimeout(60000).build();		//设置响应超时时间
            httpPost.setConfig(config);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (entity != null) {
                body = EntityUtils.toString(httpEntity, "UTF-8");
            }
            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * httpclient get
     * @param url
     * @param params
     * @return
     */
    public static String sendGet(String url, Map<String, Object> params) {
        try {
            String body = null;
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
                }
            }
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.setParameters(nvps);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            //httpClient配置
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(60000)		//设置连接超时时间
                    .setSocketTimeout(60000).build();		//设置响应超时时间
            httpGet.setConfig(config);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                body = EntityUtils.toString(httpEntity, "UTF-8");
            }
            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * httpclient post
     * @param url 请求地址
     * @param json 请求的json数据
     * @return
     */
    public String jsonPost(String url, String json) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        String result = null;
        try {
            StringEntity s = new StringEntity(json);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            httpPost.setEntity(s);
            //httpClient配置
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(60000)		//设置连接超时时间
                    .setSocketTimeout(60000).build();		//设置响应超时时间
            httpPost.setConfig(config);
            HttpResponse res = httpclient.execute(httpPost);
            result = EntityUtils.toString(res.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

}
