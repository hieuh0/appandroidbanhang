package com.example.hieuho.doankhoapham.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.hieuho.doankhoapham.Database_SQLite.MyDatabase;
import com.example.hieuho.doankhoapham.Helper.AppConfig;
import com.example.hieuho.doankhoapham.Helper.TaiKhoan;
import com.example.hieuho.doankhoapham.R;
import android.app.ProgressDialog;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Register_Activity extends AppCompatActivity {
    private static final String TAG = Register_Activity.class.getSimpleName();
    EditText edtTen,edtMail,edtPass;
    Button btnRegist,btnLogin;
    MyDatabase db;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new MyDatabase(Register_Activity.this);
        edtTen=(EditText)findViewById(R.id.name);
        edtMail=(EditText)findViewById(R.id.email);
        edtPass=(EditText)findViewById(R.id.password);
        btnRegist = (Button)findViewById(R.id.btnRegister);
        btnLogin = (Button)findViewById(R.id.btnDangKi);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ten = edtTen.getText().toString();
                String email =edtMail.getText().toString();
                String matkhau = edtPass.getText().toString();
                if(matkhau.length() < 4 || matkhau.trim().equals("")){
                    edtPass.setError("Mật Khẩu Phải Có Nhiều Hơn 4 kí tự và không được để trống");
                } if(ten.length()<2 || ten.trim().equals("")){

                    edtTen.setError("Vui Lòng Nhập Họ Tên");
                }if(email.length()<10 || email.trim().equals("")){
                    edtMail.setError("Vui Lòng Nhập Email");
                }
                else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new DangKi().execute();
                        }
                    });
                }
            }
        });
    }
    private class DangKi extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... params) {
            return makePostRequest();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplication(),s,Toast.LENGTH_LONG).show();
        }
    }

    private String makePostRequest() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(AppConfig.URL_REGISTER);
        String ten = edtTen.getText().toString();
        String email =edtMail.getText().toString();
        String matkhau = edtPass.getText().toString();
        List nameValuePair = new ArrayList(2);
        nameValuePair.add(new BasicNameValuePair("name", ten));
        nameValuePair.add(new BasicNameValuePair("email", email));
        nameValuePair.add(new BasicNameValuePair("encrypted_password",matkhau));
        nameValuePair.add(new BasicNameValuePair("created_at",null));

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
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
