package com.example.refuel.torweather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.refuel.torweather.Model.Weather_7;
import com.example.refuel.torweather.R;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Weather_7> {

        private int resourceId;

        public ListViewAdapter(Context context, int resource , List<Weather_7> object) {
            super(context, resource , object);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Weather_7 w7 = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId , parent , false);

            TextView time = view.findViewById(R.id.time_list_text);
            TextView tianqi = view.findViewById(R.id.tianqi_list_text);
            TextView wendu = view.findViewById(R.id.wendu_list_text);

            time.setText(w7.getTime());
            tianqi.setText(w7.getWeather());
            wendu.setText(w7.getMax()+" | "+w7.getMin());

            return view;

    }
}
