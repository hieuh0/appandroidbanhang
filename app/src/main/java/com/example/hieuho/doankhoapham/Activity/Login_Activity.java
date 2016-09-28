package com.example.hieuho.doankhoapham.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hieuho.doankhoapham.Database_SQLite.MyDatabase;
import com.example.hieuho.doankhoapham.Helper.AppConfig;
import com.example.hieuho.doankhoapham.Helper.SessionManager;
import com.example.hieuho.doankhoapham.Helper.TaiKhoan;
import com.example.hieuho.doankhoapham.MainActivity;
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


public class Login_Activity extends AppCompatActivity {
    private static final String TAG = Register_Activity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    SessionManager session;
    MyDatabase db;
    TextView txtQuen;
    public static final String USER_NAME = "USERNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new MyDatabase(Login_Activity.this);
        session = new SessionManager(Login_Activity.this);
        if (session.isLoggedIn()) {
            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        txtQuen = (TextView)findViewById(R.id.txtQuen);
        txtQuen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this,ResetPass.class);
                startActivity(intent);
            }
        });
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button)findViewById(R.id.btnTao);
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this,Register_Activity.class);
                startActivity(intent);
            }
        });
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

    }

    public void onCLick(View view){
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String tag_string_req = "req_login";
        pDialog.setMessage("Đang Đăng Nhập ...");
        showDialog();
        if(email.trim().equals("")){
            hideDialog();
            inputEmail.setError("Vui Lòng Nhập Email");
        }if(password.trim().equals("")){
            hideDialog();
            inputPassword.setError("Vui Lòng Nhập Email");
        } else {
            login(email,password);
            TaiKhoan tk = new TaiKhoan(email,password);
            db.CreateAcc(tk);
        }
    }

    private void login(final String username, String password) {

        class LoginAsync extends AsyncTask<String, Void, String>{


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
                            AppConfig.URL_LOGIN);
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
                    session.setLogin(true);
                    Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    hideDialog();
                    Toast.makeText(getApplicationContext(), "Sai Email Hoặc Mật Khẩu", Toast.LENGTH_LONG).show();
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(username, password);
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



