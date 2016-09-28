package com.example.hieuho.doankhoapham.Fragment_Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hieuho.doankhoapham.Database_SQLite.MyDatabase;
import com.example.hieuho.doankhoapham.Helper.AppConfig;
import com.example.hieuho.doankhoapham.Helper.Product_Handler;
import com.example.hieuho.doankhoapham.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HieuHo on 7/24/2016.
 */
public class Ban_Fragment extends Fragment {
    String danhmuc[] = {"Xe Cộ", "Đồ Điện Tử", "Thời Trang", "Nội Thất"};
    EditText edtTenSP, edtGia, edtMota, edtDT;
    Spinner Spdanhmuc;
    Button btnDang;
    ImageView imvCamera;
    MyDatabase db;
    int CODE = 888;
    private int REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ban_layout,container,false);
        edtTenSP = (EditText)view.findViewById(R.id.edtTenSP);
        edtGia = (EditText) view.findViewById(R.id.edtGia);
        edtMota = (EditText) view.findViewById(R.id.edtMoTa);
        edtDT = (EditText) view.findViewById(R.id.edtDT);
        Spdanhmuc = (Spinner) view.findViewById(R.id.SPdanhmuc);
        imvCamera = (ImageView) view.findViewById(R.id.imvCamera);
        db = new MyDatabase(getActivity());
        btnDang = (Button) view.findViewById(R.id.btnDangBan);
        btnDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensp = edtTenSP.getText().toString();
                String giasp = edtGia.getText().toString();
                String mota = edtMota.getText().toString();
                String hinh = ImageView_To_String(imvCamera);
                String dt = edtDT.getText().toString();
                int dm = Spdanhmuc.getSelectedItemPosition();
                if (tensp.trim().equals("") || tensp.length() < 5) {
                    edtTenSP.setError("Nhập Tiêu Đề Sản Phẩm và Tối Thiếu 5 Kí Tự");
                }
                if (giasp.trim().equals("")) {
                    edtTenSP.setError("Nhập Giá Sản Phẩm");
                }
                if (mota.trim().equals("") || mota.length() < 5) {
                    edtTenSP.setError("Nhập Giá Sản Phẩm và Tối Thiếu 5 Kí Tự");
                }
                if (dt.trim().equals("") || dt.length() < 10) {
                    edtTenSP.setError("Nhập Số Điện Thoại và Tối Thiếu 5 Kí Tự");
                } else {

                    try {
                        Product_Handler p = new Product_Handler(ImageView_To_Byte(imvCamera),tensp,giasp,mota,dm,dt);
                        db.CreateSP(p);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),e+"",Toast.LENGTH_LONG).show();
                    }
                    DialogMesseger();

                }
            }
        });
        imvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CODE);

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, danhmuc);
        Spdanhmuc.setAdapter(adapter);

        return view;
    }




    public class DangBan extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return makePostRequest();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }

    private String makePostRequest() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(AppConfig.URL_BAN);
        String tensp = edtTenSP.getText().toString();
        String giasp = edtGia.getText().toString();
        String mota = edtMota.getText().toString();
        String hinh = ImageView_To_String(imvCamera);
        String dt = edtDT.getText().toString();
        List nameValuePair = new ArrayList(2);
        nameValuePair.add(new BasicNameValuePair("tieude", tensp));
        nameValuePair.add(new BasicNameValuePair("hinh", hinh));
        nameValuePair.add(new BasicNameValuePair("mota", mota));
        nameValuePair.add(new BasicNameValuePair("gia", giasp));
        nameValuePair.add(new BasicNameValuePair("sodienthoai", dt));
        nameValuePair.add(new BasicNameValuePair("danhmuc", Integer.toString(Spdanhmuc.getSelectedItemPosition())));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String kq = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kq;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imvCamera.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] ImageView_To_Byte(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public String ImageView_To_String(ImageView iv) {
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String strHinh = Base64.encodeToString(byteArray, 0);
        return strHinh;
    }

    public void DialogMesseger(){
        final android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Thông Báo");
        builder.setMessage("Bạn Có Muốn Đăng Bán Không?");
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new DangBan().execute();
                    }
                });
            }
        });
        builder.setPositiveButton("Không",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
        builder.create().show();
    }
}

