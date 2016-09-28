package com.example.hieuho.doankhoapham.Fragment_Menu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hieuho.doankhoapham.Adapter.Adapter_SQL;
import com.example.hieuho.doankhoapham.Database_SQLite.MyDatabase;
import com.example.hieuho.doankhoapham.Helper.SanPham;
import com.example.hieuho.doankhoapham.R;

import java.util.ArrayList;

/**
 * Created by HieuHo on 7/22/2016.
 */
public class Cart_Fragment extends Fragment {
    MyDatabase db;
    Adapter_SQL adapter;
    ListView lv;
    ArrayList<SanPham> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_layout,container,false);
        db = new MyDatabase(getActivity());
        lv= (ListView)view.findViewById(R.id.list);
        list=db.SelectSP();
        adapter = new Adapter_SQL(getActivity(),list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SanPham p = list.get(position);
                Toast.makeText(getActivity(),p.id+"",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
