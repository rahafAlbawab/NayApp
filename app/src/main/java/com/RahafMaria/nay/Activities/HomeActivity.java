package com.RahafMaria.nay.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.RahafMaria.nay.Adpaters.HomeViewPagerAdpater;
import com.RahafMaria.nay.R;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    ImageView navigation_drawer_icon;
    ImageView cart_icon;
    ImageView report_icon;
    DrawerLayout drawerLayout;
    RelativeLayout drawer_content;
    LinearLayout contact_us_drawer;
    LinearLayout about_us_drawer;
    LinearLayout celebrities_drawer;
    LinearLayout celebrities_chat_drawer;
    LinearLayout logout_drawer;
    TabLayout tabLayout;
    ViewPager viewPager;
    HomeViewPagerAdpater homeViewPagerAdpater;
    public static Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static int order_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        Initialization();

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        homeViewPagerAdpater = new HomeViewPagerAdpater(getSupportFragmentManager());
        viewPager.setAdapter(homeViewPagerAdpater);
        tabLayout.setupWithViewPager(viewPager);
        listeners();
        if (sharedPreferences.getString("order_id", "0").equalsIgnoreCase("0")) {
            order_id = 1;
        } else {
            order_id = Integer.parseInt(sharedPreferences.getString("order_id", "0"));
        }
    }

    public void Initialization() {
        navigation_drawer_icon = findViewById(R.id.navigation_drawer_icon);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_content = findViewById(R.id.left_drawer);
        contact_us_drawer = findViewById(R.id.contact_us_drawer);
        about_us_drawer = findViewById(R.id.about_us_drawer);
        celebrities_drawer = findViewById(R.id.celebrities_drawer);
        celebrities_chat_drawer = findViewById(R.id.celebrities_chat_drawer);
        logout_drawer = findViewById(R.id.logout_drawer);
        cart_icon = findViewById(R.id.cart_icon);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        context = HomeActivity.this;
        sharedPreferences = getSharedPreferences("loginChecked", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        report_icon = findViewById(R.id.report_icon);


    }

    public void listeners() {

        navigation_drawer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(drawer_content);
            }
        });
        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                intent.putExtra("ActivityName", "HomeActivity");
                startActivity(intent);


            }
        });
        report_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
                intent.putExtra("ActivityName", "HomeActivity");
                startActivity(intent);

            }
        });

        contact_us_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ContactUsActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(drawer_content);

            }
        });
        about_us_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(drawer_content);

            }
        });
        celebrities_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CelebritiesActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(drawer_content);

            }
        });
        celebrities_chat_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CelebritiesChatListActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(drawer_content);

            }
        });
        logout_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                editor.putString("isLogged", "no");
                editor.commit();
                drawerLayout.closeDrawer(drawer_content);
                startActivity(intent);

            }
        });
        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);

            }
        });


    }
}