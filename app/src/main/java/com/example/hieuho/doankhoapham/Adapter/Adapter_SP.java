package com.example.hieuho.doankhoapham.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hieuho.doankhoapham.Helper.SanPham;
import com.example.hieuho.doankhoapham.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by HieuHo on 7/29/2016.
 */
public class Adapter_SP extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<SanPham> data= Collections.emptyList();

    public Adapter_SP(Context context, List<SanPham> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_fish, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        SanPham current=data.get(position);
        myHolder.textFishName.setText(current.tieude);
        myHolder.textType.setText("Ngày: " + current.ngay);
        myHolder.textPrice.setText("Giá. " +doitien(current.gia ));
        String_To_ImageView(current.hinh,myHolder.ivFish);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{

        TextView textFishName;
        ImageView ivFish;
        TextView textSize;
        TextView textType;
        TextView textPrice;
        public MyHolder(View itemView) {
            super(itemView);
            textFishName= (TextView) itemView.findViewById(R.id.textFishName);
            ivFish= (ImageView) itemView.findViewById(R.id.ivFish);
            textType = (TextView) itemView.findViewById(R.id.textType);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
        }

    }
    public void String_To_ImageView(String strBase64, ImageView iv){
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv.setImageBitmap(decodedByte);
    }
    public String doitien(String x)
    {
        String chuoi=String.valueOf(x);
        double d=Double.parseDouble(chuoi);
        DecimalFormat f=new DecimalFormat("#,### đ");
        return f.format(d);
    }
}
