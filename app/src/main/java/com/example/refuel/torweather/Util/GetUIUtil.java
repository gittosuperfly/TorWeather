package com.example.refuel.torweather.Util;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;

public class GetUIUtil {

    private GetUIUtil (){}

    /**
     * 改变ListView高度
     * @param listView 传入ListView
     * @param hight 高度 像素单位
     */
    public static void listViewHight(ListView listView , int hight){
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = hight;
    }
}
