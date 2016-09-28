package com.example.hieuho.doankhoapham.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuho.doankhoapham.Adapter.Adapter_SP;
import com.example.hieuho.doankhoapham.Adapter.SanPhamAdapter;
import com.example.hieuho.doankhoapham.Helper.SanPham;
import com.example.hieuho.doankhoapham.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class GetProduct extends AppCompatActivity {
    int id;
    private static final String TAG_RESULTS="sanpham";
    JSONArray peoples = null;
    String myJSON;
    ArrayList<SanPham> list;
    List<SanPham> data=new ArrayList<>();
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_product);
        Intent intent = getIntent();
        id = intent.getIntExtra("data",0);
        getData();
        lv=(ListView)findViewById(R.id.listsanpham);
    }
    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

          for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                data.add(new SanPham(
                        c.getInt("id"),
                        c.getString("tieude"),
                        c.getString("mota"),
                        c.getString("gia"),
                        c.getString("sodienthoai"),
                        c.getString("ngay"),
                        c.getString("hinh")
                ));
            }
            SanPhamAdapter adapter = new SanPhamAdapter(getApplication(),R.layout.customlistdanhmuc,data);
            lv.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String>{


            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://hoduchieu.esy.es/xemtheoid.php?id="+id);
                httppost.setHeader("Content-type", "application/json");
                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    inputStream = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }
}



