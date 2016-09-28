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

public class ResetPass extends AppCompatActivity {
    EditText edtMail;
    Button btnTim;
    View view;
    private static final String TAG = Register_Activity.class.getSimpleName();
    public static final String CODE = "CODE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        edtMail = (EditText)findViewById(R.id.txtEmail);
        btnTim = (Button)findViewById(R.id.btnGoiSever);
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtMail.getText().toString();
                if(email.length() < 5 || email.trim().equals("")){
                    edtMail.setError("Vui Lòng Nhập Mail Đúng Định Dạng");
                }else {
                    Tim(email);
                }
            }
        });
    }

    private void Tim(final String mail) {

        class TimMK extends AsyncTask<String, Void, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();}

            @Override
            protected String doInBackground(String... params) {

                String email = params[0];
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email", email));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            AppConfig.URL_RESET);
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
                    Intent intent = new Intent(ResetPass.this,NewPass.class);
                   intent.putExtra(CODE,mail);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "email Khong Ton Tai", Toast.LENGTH_LONG).show();
                }
            }
        }
        TimMK la = new TimMK();
        la.execute(mail);
    }



    ///update mk
/*
   */
}
