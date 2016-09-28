package com.example.hieuho.doankhoapham;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hieuho.doankhoapham.Activity.Login_Activity;
import com.example.hieuho.doankhoapham.Activity.Search_activity;
import com.example.hieuho.doankhoapham.Database_SQLite.MyDatabase;
import com.example.hieuho.doankhoapham.Fragment_Menu.Ban_Fragment;
import com.example.hieuho.doankhoapham.Fragment_Menu.Cart_Fragment;
import com.example.hieuho.doankhoapham.Fragment_Menu.Category_Fragment;
import com.example.hieuho.doankhoapham.Fragment_Menu.Contacs_Fragment;
import com.example.hieuho.doankhoapham.Fragment_Menu.Intro_Fragment;
import com.example.hieuho.doankhoapham.Fragment_Menu.News_Fragment;
import com.example.hieuho.doankhoapham.Fragment_Menu.Accout_Fragment;
import com.example.hieuho.doankhoapham.Helper.SessionManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MyDatabase db;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MyDatabase(MainActivity.this);
        session = new SessionManager(MainActivity.this);
        CheckInternet();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Fragment fr = new Category_Fragment();
        setTitle("Danh Mục");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content, fr).commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Fragment fragment  = new Ban_Fragment();
                setTitle("Đăng Bán");
                if (!session.isLoggedIn()) {
                    logoutUser();
                }
               FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment).commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
              Intent intent = new Intent(getApplication(),Search_activity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fr = null;
        int id = item.getItemId();
        if (id == R.id.category_nav) {
            fr = new Category_Fragment();
            setTitle("Danh Mục");
            CheckInternet();
        } else if (id == R.id.cart_nav) {
            fr = new Cart_Fragment();
            setTitle("Giỏ Hàng");
            CheckInternet();
            if (!session.isLoggedIn()) {
                logoutUser();
            }
        } else if(id==R.id.intro_nav){
            fr = new Intro_Fragment();
            setTitle("Về Chúng Tôi");
            CheckInternet();
        }else if (id == R.id.contact_nav) {
            fr = new Contacs_Fragment();
            setTitle("Liên Hệ");
            CheckInternet();
        } else if (id == R.id.accout_nav) {
            fr = new Accout_Fragment();
            setTitle("Tài Khoản");
            if (!session.isLoggedIn()) {
                logoutUser();
            }
            ;
        }else if(id==R.id.intro_nav){
            fr = new Intro_Fragment();
            setTitle("Về Chúng Tôi");
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content, fr).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void logoutUser() {
        session.setLogin(false);
        db.DeleteUser();
        Intent intent = new Intent(MainActivity.this, Login_Activity.class);
        startActivity(intent);
        finish();
    }
    public final boolean CheckInternet() {
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
           DialogMessegerInternet();
            return false;
        }
        return false;
    }
    public void DialogMessegerInternet(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Thông Báo");
        builder.setMessage("Vui Lòng Kiểm Tra Kết Nối Internet!");
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}

