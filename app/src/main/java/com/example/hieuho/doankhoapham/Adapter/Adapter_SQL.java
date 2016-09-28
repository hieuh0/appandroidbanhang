package com.example.hieuho.doankhoapham.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieuho.doankhoapham.Helper.SanPham;
import com.example.hieuho.doankhoapham.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by HieuHo on 7/30/2016.
 */
public class Adapter_SQL extends BaseAdapter {

    Context c;
    ArrayList<SanPham> list;
    public Adapter_SQL(Context c, ArrayList<SanPham> list){
        this.c=c;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
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
        LayoutInflater inflater  =((Activity)c).getLayoutInflater();
        view=inflater.inflate(R.layout.customsp, null);
        ImageView iv = (ImageView)view.findViewById(R.id.imageView);
        TextView txt= (TextView)view.findViewById(R.id.ten);
        TextView txt1 = (TextView)view.findViewById(R.id.gia);
        TextView txt2= (TextView)view.findViewById(R.id.ngay);
        SanPham sp = list.get(position);
        txt.setText("Tieu De:"+sp.tieude+"");
        txt2.setText("Ngay:"+sp.ngay+"");
        txt1.setText("Gia:"+doitien(sp.gia)+"");
        Bitmap bitmap = BitmapFactory.decodeByteArray(sp.H,0,sp.H.length);
        iv.setImageBitmap(bitmap);
        return view;
    }
    public String doitien(String x)
    {
        String chuoi=String.valueOf(x);
        double d=Double.parseDouble(chuoi);
        DecimalFormat f=new DecimalFormat("#,### đ");
        return f.format(d);
    }
}
