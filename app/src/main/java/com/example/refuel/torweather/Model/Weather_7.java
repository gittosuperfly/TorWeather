package com.example.refuel.torweather.Model;

/**
 * 未来七天天气的实体类
 */
public class Weather_7 {

    private String time , weather , max , min ;

    /**
     * 构造器
     * @param time 时间
     * @param weather 天气
     * @param max 最高温度
     * @param min 最低温度
     */
    public Weather_7(String time, String weather, String max, String min) {
        this.time = time;
        this.weather = weather;
        this.max = max;
        this.min = min;
    }

    /**
     * getter and setter
     * @return 值
     */

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
