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

import com.example.refuel.torweather.Model.LifeIndex;
import com.example.refuel.torweather.Model.Weather_7;
import com.example.refuel.torweather.R;

import java.util.List;

public class LifeIndexAdapter extends ArrayAdapter<LifeIndex> {

    private int resourceId;

    public LifeIndexAdapter(Context context, int resource , List<LifeIndex> object) {
        super(context, resource , object);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LifeIndex life = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId , parent , false);

        TextView name = view.findViewById(R.id.life_list_name);
        TextView title = view.findViewById(R.id.life_title_text);
        TextView text = view.findViewById(R.id.life_list_text);
        ImageView image = view.findViewById(R.id.life_list_image);

        name.setText(life.getName());
        title.setText(life.getTitle());
        text.setText(life.getText());
        image.setImageResource(life.getImageId());

        return view;

    }
}
