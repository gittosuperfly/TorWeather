package com.example.refuel.torweather.Util;

import com.example.refuel.torweather.Model.Root;
import com.google.gson.Gson;

/**
 * 建议完成Gson操作
 * 返回一个Root类
 *
 * **********|| 要注意Json类的起始类一定要叫 Root ||**********
 */

public class GsonUtil {

    private static Gson gson = new Gson();

    private GsonUtil() {}

    public static Root getRoot(String Json){
        Root root = gson.fromJson(Json , Root.class);
        return root;
    }
}
