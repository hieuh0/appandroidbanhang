package com.example.hieuho.doankhoapham.Fragment_Menu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hieuho.doankhoapham.Activity.Product_Activity;
import com.example.hieuho.doankhoapham.Adapter.Adapter_Category;
import com.example.hieuho.doankhoapham.R;

/**
 * Created by HieuHo on 7/22/2016.
 */
public class Category_Fragment extends Fragment {
    ListView listView;
    String TenDM[] = {"Xe Cộ","Đồ Điện Tử","Thời Trang","Nội Thất"};
    int hinh[] = {R.drawable.xe,R.drawable.device,R.drawable.ao,R.drawable.sofa};
    Adapter_Category adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_layout,container,false);
        Bundle bundle = this.getArguments();
/*        String myValue = bundle.getString("message");
        Toast.makeText(getActivity(), "this is to do list " + myValue, Toast.LENGTH_SHORT).show();*/
        listView = (ListView)view.findViewById(R.id.listviewcate);
        adapter = new Adapter_Category(getActivity(),TenDM,hinh);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition     = position;
                Intent myIntent = new Intent(getActivity(), Product_Activity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.putExtra("id", itemPosition);
                startActivityForResult(myIntent, 0);
            }
        });
        return view;
    }
}
