package com.ds.utils;

import cn.hutool.core.util.IdUtil;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
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
    public static String jsonPost(String url, String json) {
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


    /**
     * 上传文件
     * @param url
     * @param filePath
     * @throws ParseException
     * @throws IOException
     */
    public static String postFile(String url, String filePath) throws ParseException, IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;
        try{
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);
            // 把文件转换成流对象FileBody
            File file = new File(filePath);
            FileBody bin = new FileBody(file);
            StringBody uploadFileName = new StringBody(
                    IdUtil.simpleUUID(), ContentType.create(
                            "text/plain", Consts.UTF_8));
            //以浏览器兼容模式运行，防止文件名乱码。
            HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    //uploadFile对应服务端类的同名属性<File类型>
                    // 相当于<input type="file" name="file"/>
                    .addPart("file", bin)
                    //uploadFileName对应服务端类的同名属性<String类型>
                    // 相当于<input type="text" name="fileName" value=fileName>
                    .addPart("fileName", uploadFileName)
                    .setCharset(CharsetUtils.get("UTF-8")).build();
            httpPost.setEntity(reqEntity);
            //httpClient配置
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(60000)		//设置连接超时时间
                    .setSocketTimeout(60000).build();		//设置响应超时时间
            httpPost.setConfig(config);
//            System.out.println("发起请求的页面地址 "+ httpPost.getRequestLine());
            // 发起请求 并返回请求的响应
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try{
                // 打印响应状态
//                System.out.println(response.getStatusLine());
                // 获取响应对象
                HttpEntity resEntity = response.getEntity();
                if(resEntity !=null) {
                    // 打印响应长度
//                    System.out.println("Response content length: "+ resEntity.getContentLength());
                    // 打印响应内容
//                    System.out.println(EntityUtils.toString(resEntity,Charset.forName("UTF-8")));
                    result = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
                }
                // 销毁
                EntityUtils.consume(resEntity);
            }finally{
                response.close();
            }
        }finally{
            httpClient.close();
        }
        return result;
    }

    /**
     * 下载文件
     * @param  url
     * @param  destFileName   xxx.jpg/xxx.png/xxx.txt
     * @throws  ClientProtocolException
     * @throws IOException
     */
    public static void getFile(String url, String destFileName) throws ClientProtocolException, IOException{
        // 生成一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        //httpClient配置
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(60000)		//设置连接超时时间
                .setSocketTimeout(60000).build();		//设置响应超时时间
        httpGet.setConfig(config);
        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        File file = new File(destFileName);
        try{
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[1024];
            while((l = in.read(tmp)) != -1) {
                fout.write(tmp,0, l);
                // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
            }
            fout.flush();
            fout.close();
        }finally{
            // 关闭低层流。
            in.close();
        }
        httpclient.close();
    }

}
