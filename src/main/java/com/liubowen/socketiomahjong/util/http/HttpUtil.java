package com.liubowen.socketiomahjong.util.http;

import com.google.common.collect.Maps;
import io.netty.util.CharsetUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: liubowen
 * @date: 2017/11/30 12:28
 * @description: http工具类
 */
public class HttpUtil {

    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    /**
     * 发送带参数的http/https get请求
     * 
     * @param url
     *            不带参数的url
     * @param charset
     *            字符编码
     * @param params
     *            请求参数
     * @return
     */
    public static String sendGet(String url, Charset charset, Map<String, Object> params) {
        StringBuilder urlBuilder = new StringBuilder(url);
        int i = 0;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (i == 0) {
                urlBuilder.append("?");
            } else {
                urlBuilder.append("&");
            }
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
        }
        System.err.println(urlBuilder.toString());
        return sendGet(urlBuilder.toString(), charset);
    }

    /**
     * 发送带参数的http/https get请求(默认字符编码为"UTF-8")
     * 
     * @param url
     *            不带参数的url
     * @param params
     *            请求参数
     * @return 返回信息
     */
    public static String sendGet(String url, Map<String, Object> params) {
        return sendGet(url, CharsetUtil.UTF_8, params);
    }

    /**
     * 发送不带参数的http/https get请求(默认字符编码为"UTF-8")
     * 
     * @param url
     *            请求地址
     * @return
     */
    public static String sendGet(String url) {
        return sendGet(url, Consts.UTF_8);
    }

    /**
     * 发送不带参数的http/https post请求
     * 
     * @param url
     *            请求地址
     * @param charset
     *            字符编码
     * @return
     */
    public static String sendGet(String url, Charset charset) {
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = null;
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送不带参数的http/https post请求(默认字符编码为"UTF-8")
     * 
     * @param url
     *            请求地址
     * @param map
     *            请求参数
     * @return
     */
    public static String sendPost(String url, Map<String, String> map) {
        return sendPost(url, Consts.UTF_8, map);
    }

    /**
     * 发送带参数的http/https post请求
     * 
     * @param url
     *            请求地址
     * @param charset
     *            字符编码
     * @param map
     *            参数
     * @return
     */
    public static String sendPost(String url, Charset charset, Map<String, String> map) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity1 = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity1, charset);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送不带参数的http/https post请求 (默认字符编码为"UTF-8")
     * 
     * @param url
     *            请求地址
     * @return
     */
    public static String sendPost(String url) {
        return sendPost(url, Consts.UTF_8);
    }

    /**
     * 发送不带参数的http/https post请求
     * 
     * @param url
     *            请求地址
     * @param charset
     *            编码类型
     * @return
     */
    public static String sendPost(String url, Charset charset) {
        HttpPost httppost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity, charset);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("tn", "98012088_5_dg");
        map.put("ch", "12");
        String result = HttpUtil.sendGet("https://www.baidu.com/", map);
        System.err.println("result : " + result);
    }

}
