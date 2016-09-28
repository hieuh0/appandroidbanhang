package com.example.hieuho.doankhoapham.Fragment_Menu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuho.doankhoapham.Activity.Login_Activity;
import com.example.hieuho.doankhoapham.Adapter.Adapter_User;
import com.example.hieuho.doankhoapham.Database_SQLite.MyDatabase;
import com.example.hieuho.doankhoapham.Helper.SessionManager;
import com.example.hieuho.doankhoapham.Helper.TaiKhoan;
import com.example.hieuho.doankhoapham.R;

import java.util.ArrayList;

/**
 * Created by HieuHo on 7/22/2016.
 */
public class Accout_Fragment extends Fragment {
Button btn,btnLogout;
SessionManager session;
    MyDatabase db;
    ListView ListUser;
    ArrayList<TaiKhoan> arrayList;
    Adapter_User adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accout_layout,container,false);
        ListUser = (ListView)view.findViewById(R.id.ListViewUser);
        db=new MyDatabase(getActivity());
        arrayList = db.SelectUser();
        adapter = new Adapter_User(getActivity(),arrayList);
        ListUser.setAdapter(adapter);
        btnLogout = (Button)view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        session = new SessionManager(getActivity());
        db= new MyDatabase(getActivity());
        return view;
    }
    private void logoutUser() {
        session.setLogin(false);
        db.DeleteUser();
        Intent intent = new Intent(getActivity(), Login_Activity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
