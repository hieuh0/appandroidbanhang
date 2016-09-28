package com.example.hieuho.doankhoapham.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hieuho.doankhoapham.Helper.AppConfig;
import com.example.hieuho.doankhoapham.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NewPass extends AppCompatActivity {
    EditText edtCode,edtPass;
    Button btnDoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        Intent intent = getIntent();
        String code = intent.getStringExtra(ResetPass.CODE);
        edtCode = (EditText)findViewById(R.id.codeGoi);
        edtCode.setText(code);
        edtCode.setEnabled(false);
        edtPass = (EditText)findViewById(R.id.newpass);
        btnDoi=(Button)findViewById(R.id.btnDoi);
        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edtCode.getText().toString();
                String pass= edtPass.getText().toString();
                if(pass.length() <5 || pass.trim().equals("")){
                    edtPass.setError("Vui Lòng Nhập Mật Khẩu Mới và Lớn Hơn 5 Kí Tự");
                }
                else {
                    Doi(code,pass);
                }
            }
        });
    }
    private void Doi(final String username,final String password) {

        class DoiMK extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();}

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email", uname));
                nameValuePairs.add(new BasicNameValuePair("encrypted_password", pass));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            AppConfig.URL_RESETNEW);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                if(s.equalsIgnoreCase("success")){
                    Intent intent = new Intent(NewPass.this,Login_Activity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Loi", Toast.LENGTH_LONG).show();
                }
            }
        }
        DoiMK la = new DoiMK();
        la.execute(username,password);
    }


}

