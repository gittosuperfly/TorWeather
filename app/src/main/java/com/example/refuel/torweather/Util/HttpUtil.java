package com.example.refuel.torweather.Util;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * OkHttp网络请求工具类
 */

public class HttpUtil {
    /**
     * 1、创建OkHttpClient对象
     * 2、变为静态方法
     */
    private static OkHttpClient client= new OkHttpClient();

    private HttpUtil(){}

    /**
     * post请求方法
     * @param url 地址
     * @param body 封装的RequsetBody实例
     * @param callback 回调
     */
    public static void post(String url , RequestBody body , Callback callback){
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * get请求方法
     * @param url 地址
     * @param callback 回调
     */
    public static void get(String url , Callback callback){
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
}