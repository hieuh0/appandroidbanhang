package com.example.hieuho.doankhoapham.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuho.doankhoapham.Adapter.Adapter_SP;
import com.example.hieuho.doankhoapham.Adapter.SanPhamAdapter;
import com.example.hieuho.doankhoapham.Helper.AppConfig;
import com.example.hieuho.doankhoapham.Helper.SanPham;
import com.example.hieuho.doankhoapham.R;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Product_Activity extends AppCompatActivity {
    ListView LVSp;
    ArrayList<SanPham> list;
    TextView tv;
    List<SanPham> data=new ArrayList<>();
    String a[] = {"and","aa"};
    String myJSON;
    private RecyclerView mRVFishPrice;
    private Adapter_SP mAdapter;
    private static final String TAG_RESULTS="sanpham";
    JSONArray peoples = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        list = new ArrayList<SanPham>();
        getData();
        mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
        mRVFishPrice.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRVFishPrice, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SanPham movie = data.get(position);
                Intent intent = new Intent(Product_Activity.this,GetProduct.class);
                intent.putExtra("data",movie.id);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Product_Activity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Product_Activity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
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
                            c.getString("gia"),
                            c.getString("ngay"),
                            c.getString("hinh")
                        ));
            }
            mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
            mAdapter = new Adapter_SP(Product_Activity.this, data);
            mRVFishPrice.setAdapter(mAdapter);
            mRVFishPrice.setLayoutManager(new LinearLayoutManager(Product_Activity.this));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String>{
                Bundle extras = getIntent().getExtras();
                int temp = extras.getInt("id");

            @Override
        protected String doInBackground(String... params) {
           DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
           HttpPost httppost = new HttpPost("http://hoduchieu.esy.es/xemhang.php?danhmuc="+temp);
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
