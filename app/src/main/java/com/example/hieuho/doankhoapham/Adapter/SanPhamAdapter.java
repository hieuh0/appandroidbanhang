package com.example.hieuho.doankhoapham.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieuho.doankhoapham.Helper.SanPham;
import com.example.hieuho.doankhoapham.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HieuHo on 7/28/2016.
 */
public class SanPhamAdapter extends BaseAdapter {
    Context mycontext;
    int myLayout;
    List<SanPham> arraysanpham;

    public SanPhamAdapter(Context c, int layout, List<SanPham> sanphamlist){
        mycontext =c;
        myLayout=layout;
        arraysanpham = sanphamlist;
    }
    @Override
    public int getCount() {
        return arraysanpham.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout,null);
        TextView tv = (TextView)convertView.findViewById(R.id.txtTieuDe);
        tv.setText("Tên: "+arraysanpham.get(position).tieude);
        TextView tv1 = (TextView)convertView.findViewById(R.id.txtGia);
        tv1.setText(doitien(arraysanpham.get(position).gia));
        TextView tv2 = (TextView)convertView.findViewById(R.id.txtNgay);
        tv2.setText("Ngày: "+arraysanpham.get(position).ngay);
        TextView tv4 = (TextView)convertView.findViewById(R.id.txtMota);
        tv4.setText("Mô Tả: "+arraysanpham.get(position).mota);
        ImageView iv = (ImageView)convertView.findViewById(R.id.imvSp);
        String_To_ImageView(arraysanpham.get(position).hinh,iv);
        Button btn = (Button)convertView.findViewById(R.id.btngoi);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+arraysanpham.get(position).dienthoai));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(callIntent);

            }
        });
        return convertView;
    }
    public String doitien(String x)
    {
        String chuoi=String.valueOf(x);
        int d=Integer.parseInt(chuoi);
        DecimalFormat f=new DecimalFormat("#,### đ");
        return f.format(d);
    }
    public void String_To_ImageView(String strBase64, ImageView iv){
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv.setImageBitmap(decodedByte);
    }
}
