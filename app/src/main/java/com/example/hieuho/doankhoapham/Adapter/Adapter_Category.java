package com.example.hieuho.doankhoapham.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieuho.doankhoapham.R;

/**
 * Created by HieuHo on 7/24/2016.
 */
public class Adapter_Category extends BaseAdapter{
    Context myContext;
    int Hinh[];
    String Chu[];
    public Adapter_Category(Context myContext,  String[] Chu, int[] hinh){
        this.myContext = myContext;
        this.Chu=Chu;
        this.Hinh=hinh;
    }

    @Override
    public int getCount() {
        return Chu.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater  =((Activity)myContext).getLayoutInflater();
        view=inflater.inflate(R.layout.custom_item, null);
        TextView tvTen = (TextView) view.findViewById(R.id.tvTen);
        tvTen.setText(Chu[position]);
        ImageView imageView = (ImageView)view.findViewById(R.id.ImgHinh);
        imageView.setImageResource(Hinh[position]);
        return view;
    }
}
