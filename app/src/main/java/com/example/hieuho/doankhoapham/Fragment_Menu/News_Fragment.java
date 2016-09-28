package com.example.hieuho.doankhoapham.Fragment_Menu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hieuho.doankhoapham.R;

/**
 * Created by HieuHo on 7/22/2016.
 */
public class News_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_layout,container,false);
        return view;
    }
}
