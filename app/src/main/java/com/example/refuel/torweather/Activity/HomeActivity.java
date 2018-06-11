package com.example.refuel.torweather.Activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.refuel.torweather.Adapter.LifeIndexAdapter;
import com.example.refuel.torweather.Adapter.ListViewAdapter;
import com.example.refuel.torweather.Model.Daily_forecast;
import com.example.refuel.torweather.Model.LifeIndex;
import com.example.refuel.torweather.Model.Lifestyle;
import com.example.refuel.torweather.Model.Root;
import com.example.refuel.torweather.Model.Weather_7;
import com.example.refuel.torweather.R;
import com.example.refuel.torweather.Util.GsonUtil;
import com.example.refuel.torweather.Util.HttpUtil;
import com.example.refuel.torweather.Util.WeatherImageUtil;
import com.example.refuel.torweather.View.mListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    public String city;
    private List<Weather_7> weatherlist = new ArrayList<Weather_7>();
    private List<LifeIndex> lifeIndexList = new ArrayList<LifeIndex>();
    private ListViewAdapter adapter;
    private LifeIndexAdapter lifeAdapter;
    private Toolbar toolbar;
    private mListView listView;
    private mListView lifeListView;
    private NavigationView navigationView;
    private FloatingActionButton searchButton;
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Root root;
    private Handler mHandler = new Handler();
    private boolean canFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();

        /**
         * 加载各项布局
         */
        setToolbar();
        setSwipeRefreshLayout();
        setSearchButton();
        setOnNavigationView();

        //数据处理
        Intent intent = getIntent();
        city = intent.getStringExtra("cityIntent");
        if (city == null) Post();
        else Post(city);

    }

    /**
     * 加载View
     */
    private void initView(){
        drawerLayout = findViewById(R.id.home_drawer_layout);
        searchButton = findViewById(R.id.search_button);
        swipeRefreshLayout = findViewById(R.id.home_refresh_layout);
        toolbar = findViewById(R.id.home_toolbar);
        lifeListView = findViewById(R.id.life_list);
        navigationView = findViewById(R.id.home_avigation_view);
    }
    /**
     * 点击搜索键后操作
     */
    private void setSearchButton(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this , SearchActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    /**
     * 加载下拉刷新组件
     */
    private void setSwipeRefreshLayout(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //下拉刷新组件
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        weatherlist.clear();
                        lifeIndexList.clear();

                        if (city == null)
                            Post();
                        else
                            Post(city);

                        Toast.makeText(HomeActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 进行网络请求，并且更新UI
     */
    private void Post (){
        //定义url与body
        String url = new String("https://free-api.heweather.com/s6/weather?parameters");
        RequestBody body = new FormBody.Builder()
                .add("key","4770dcc5782c4a5bb9b1123f69b45364")
                .add("location","auto_ip")
                .build();
        //调用post方法
        HttpUtil.post(url, body ,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获得返回数据data
                String data = response.body().string();
                //更新UI

                root = GsonUtil.getRoot(data);
                setTodayWeather();
                setFutureWeather();
                setLifeIndexList();
            }
        });
    }
    private void Post (String city){
        //定义url与body
        String url = new String("https://free-api.heweather.com/s6/weather?parameters");
        RequestBody body = new FormBody.Builder()
                .add("key","4770dcc5782c4a5bb9b1123f69b45364")
                .add("location",city)
                .build();
        //调用post方法
        HttpUtil.post(url, body ,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获得返回数据data
                String data = response.body().string();
                //更新UI

                root = GsonUtil.getRoot(data);
                String satuts = root.getHeWeather6().get(0).getStatus();

                if (satuts.equals("ok")) {
                    setTodayWeather();
                    setFutureWeather();
                    setLifeIndexList();
                }else {
                    mToast("搜索城市不存在");
                    Post();
                }
            }
        });
    }

    /**
     * 连续按两次退出键退出App
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers();
        } else if (canFinish) {
            System.exit(0);
        } else {
            //连续点击两次退出的逻辑
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            canFinish = true;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    canFinish = false;
                }
            }, 5000);
        }
    }

    /**
     * 加载标题栏
     */
    private void setToolbar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                setSupportActionBar(toolbar);

                ImageView imageView = findViewById(R.id.menu);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                });
            }
        });
    }

    /**
     * 更新当天天气信息和图标
     */
    private void setTodayWeather(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = findViewById(R.id.weather_image);
                TextView weather_text = findViewById(R.id.weather_text);
                TextView wendu_text = findViewById(R.id.wendu_text);
                TextView feng_text = findViewById(R.id.feng_text);
                TextView city = findViewById(R.id.city_text);
                TextView time = findViewById(R.id.zuihou_time_text);

                WeatherImageUtil.set(imageView ,
                        root.getHeWeather6().get(0).getDaily_forecast().get(0).getCond_code_d());

                String weather = root.getHeWeather6().get(0).getDaily_forecast().get(0).getCond_txt_d();

                String wendu = root.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_max()
                        +"|"
                        +root.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_min()
                        +"℃";

                String feng = root.getHeWeather6().get(0).getDaily_forecast().get(0).getWind_dir()
                        +" "
                        +root.getHeWeather6().get(0).getDaily_forecast().get(0).getWind_sc()
                        +"级"
                        +"  紫外线强度："
                        +root.getHeWeather6().get(0).getDaily_forecast().get(0).getUv_index();

                String mcity = root.getHeWeather6().get(0).getBasic().getLocation();

                String mtime = "更新时间："+root.getHeWeather6().get(0).getUpdate().getLoc();

                weather_text.setText(weather);

                wendu_text.setText(wendu);

                feng_text.setText(feng);

                city.setText(mcity);

                time.setText(mtime);


            }
        });
    }

    /**
     * 加载七天天气
     */
    private void setFutureWeather(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initListViewWeather();
                adapter = new ListViewAdapter
                        (HomeActivity.this , R.layout.weather_list_item , weatherlist);
                listView = findViewById(R.id.weather_list);
                listView.setAdapter(adapter);
            }
        });
    }

    /**
     * 更新ListView数据
     */
    private void initListViewWeather(){

        List<Daily_forecast> Temp = root.getHeWeather6().get(0).getDaily_forecast();

        for (int i = 0 ; i < Temp.size() ; i++) {

            String time = Temp.get(i).getDate();
            String weather = Temp.get(i).getCond_txt_d();
            String max = Temp.get(i).getTmp_max();
            String min = Temp.get(i).getTmp_min();

            Weather_7 w7 = new Weather_7(time, weather, max, min);
            weatherlist.add(w7);
        }
    }

    /**
     * 加载生活指数
     */
    private void setLifeIndexList(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initListViewLife();
                lifeAdapter = new LifeIndexAdapter
                        (HomeActivity.this , R.layout.life_list_item , lifeIndexList);
                lifeListView.setAdapter(lifeAdapter);
            }
        });
    }

    /**
     * 更新LifeList数据
     */
    private void initListViewLife(){

        List<Lifestyle> temp = root.getHeWeather6().get(0).getLifestyle();

        String []name = {"舒适" , "穿衣" , "感冒" , "运动" , "旅游" , "防晒" , "洗车" , "气象"};

        int []image = {
                R.mipmap.ic_shushi ,
                R.mipmap.ic_chuanyi ,
                R.mipmap.ic_ganmao,
                R.mipmap.ic_yundong,
                R.mipmap.ic_lvxin,
                R.mipmap.ic_shexian,
                R.mipmap.ic_xiche,
                R.mipmap.ic_qixiang
        };


        for (int i = 0 ; i < 8 ; i++){
            LifeIndex index = new LifeIndex
                    (name[i] , temp.get(i).getBrf() , temp.get(i).getTxt() , image[i]);
            lifeIndexList.add(index);
        }
    }

    /**
     * 加载侧栏菜单
     */
    private void setOnNavigationView(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_item_dinwei:{
                                Intent intent = new Intent(HomeActivity.this , HomeActivity.class);
                                startActivity(intent);
                                mToast("定位成功");
                                finish();
                                break;
                            }
                            case R.id.menu_item_fenxiang:{
                                Intent textIntent = new Intent(Intent.ACTION_SEND);
                                textIntent.setType("text/plain");
                                textIntent.putExtra(Intent.EXTRA_TEXT, "快来和我一起使用简洁的洋葱天气吧！地址：http://supercai.top/2018/06/08/%E6%AC%A2%E8%BF%8E%E4%BD%BF%E7%94%A8%E6%B4%8B%E8%91%B1%E5%A4%A9%E6%B0%94/");
                                startActivity(Intent.createChooser(textIntent, "分享"));
                                break;
                            }
                            case R.id.menu_item_guanyu:{
                                Intent intent = new Intent(HomeActivity.this , MyActivity.class);
                                startActivity(intent);
                                break;
                            }
                            case R.id.menu_item_tuichu:{
                                finish();
                                break;
                            }

                        }
                        return true;
                    }
                });
            }
        });
    }

    /**
     * Toast工具
     */
    private void mToast(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(HomeActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
