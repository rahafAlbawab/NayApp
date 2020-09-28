package com.RahafMaria.nay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.RahafMaria.nay.Adpaters.CelebritiesAdapter;
import com.RahafMaria.nay.Models.ArmaniModel;
import com.RahafMaria.nay.Models.CelebritiesModel;
import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CelebritiesActivity extends AppCompatActivity {
    RecyclerView celebrities_recyclview;
    ImageView arrow_icon;
    ImageView cart_icon;
    ImageView report_icon;
    CelebritiesAdapter celebritiesAdapter;
    ArrayList<CelebritiesModel> celebritiesModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrities);
        Initialization();
        listeners();
        celebrities_recyclview.setLayoutManager(new GridLayoutManager(this, 2));
        fillCelebritiesModel();
        celebrities_recyclview.setAdapter(celebritiesAdapter);


    }

    public void Initialization() {
        celebrities_recyclview = findViewById(R.id.celebrities_recyclerview);
        arrow_icon = findViewById(R.id.arrow_icon);
        cart_icon = findViewById(R.id.cart_icon);
        celebritiesModels = new ArrayList<>();
        celebritiesAdapter = new CelebritiesAdapter(celebritiesModels, CelebritiesActivity.this);
        report_icon = findViewById(R.id.report_icon);


    }

    public void listeners() {
        arrow_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CelebritiesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        cart_icon .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CelebritiesActivity.this,CartActivity.class);
                intent.putExtra("ActivityName","CelebritiesActivity");
                startActivity(intent);

            }
        });
        report_icon .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CelebritiesActivity.this,ReportActivity.class);
                intent.putExtra("ActivityName","CelebritiesActivity");
                startActivity(intent);

            }
        });

    }

    public void fillCelebritiesModel() {
        String url = PathUrls.baseUrl + PathUrls.getCelebritiesUrl ;
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //get data from response and fill it in Array List
                                JSONObject obj = response.getJSONObject(i);
                                celebritiesModels.add(new CelebritiesModel(obj.getString("user_image"),
                                        obj.getString("name"),
                                        obj.getInt("user_id")));
                                celebritiesAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CelebritiesActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        requestQueue.add(jsonArrayRequest);


    }
}