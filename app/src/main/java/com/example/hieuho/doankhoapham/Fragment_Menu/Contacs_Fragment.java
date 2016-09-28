package com.example.hieuho.doankhoapham.Fragment_Menu;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hieuho.doankhoapham.R;

/**
 * Created by HieuHo on 7/22/2016.
 */
public class Contacs_Fragment extends Fragment {
    Button btnEmail;
    Button btnCall;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contac_layout,container,false);
        btnEmail = (Button)view.findViewById(R.id.sendEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"hieuhdps02924@fpt.edu.vn"});
                email.putExtra(Intent.EXTRA_SUBJECT, "subject");
                email.putExtra(Intent.EXTRA_TEXT, "message");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Chọn :"));
            }
        });
        btnCall = (Button)view.findViewById(R.id.Call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0946697194"));
                startActivity(callIntent);
            }
        });
        return view;
    }
}
