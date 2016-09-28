package com.example.hieuho.doankhoapham.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieuho.doankhoapham.Helper.TaiKhoan;
import com.example.hieuho.doankhoapham.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by HieuHo on 7/27/2016.
 */
public class Adapter_User extends BaseAdapter {
    Context c;
    ArrayList<TaiKhoan> list;

    public Adapter_User (Context c, ArrayList<TaiKhoan> list){
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
        view=inflater.inflate(R.layout.customz_user, null);
        ImageView iv = (ImageView)view.findViewById(R.id.imageUser);
        TextView txt1= (TextView)view.findViewById(R.id.txtmail);
        TextView txt2= (TextView)view.findViewById(R.id.txtmk);
        TaiKhoan taiKhoan = list.get(position);
        txt1.setText(taiKhoan.email+"");
        txt2.setText(convermk(taiKhoan.matkhau+""));
        return view;
    }
    public String convermk(String x)
    {
        String chuoi=String.valueOf(x);
        return chuoi.format("***");
    }

}
