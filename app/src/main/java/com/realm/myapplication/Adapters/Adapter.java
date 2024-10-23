package com.realm.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.realm.myapplication.Models.Food;
import com.realm.myapplication.R;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

   Context context;
   ArrayList<Food> food;

    public Adapter(Context context, ArrayList<Food> food) {
        this.context = context;
        this.food = food;
    }

    @Override
    public int getCount() {
        return food.size();
    }

    @Override
    public Object getItem(int position) {
        return food.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout,parent,false);

        TextView name,id;
        name = convertView.findViewById(R.id.nameText);
        id = convertView.findViewById(R.id.idText);

        id.setText(String.valueOf(food.get(position).getFood_id()));
        name.setText(food.get(position).getFood_name());
        return convertView;
    }
}
