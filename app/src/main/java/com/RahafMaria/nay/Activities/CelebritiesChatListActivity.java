package com.RahafMaria.nay.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.RahafMaria.nay.Adpaters.CelebritiesChatListAdapter;
import com.RahafMaria.nay.Adpaters.CelebrityWithChatAdapter;
import com.RahafMaria.nay.Models.CelebritiesChatListModel;
import com.RahafMaria.nay.Models.CelebritiesModel;
import com.RahafMaria.nay.Models.CelebrityWithChatModel;
import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CelebritiesChatListActivity extends AppCompatActivity {

    ImageView arrow_icon;
    ImageView cart_icon;
    ImageView report_icon;
    RecyclerView celebrities_chat_list_recyclerview;
    ArrayList<CelebritiesChatListModel> celebritiesChatListModels;
    CelebritiesChatListAdapter celebritiesChatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrities_chat_list);
        Initialization();
        listeners();
        celebrities_chat_list_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        fillCelebritiesModel();
        celebrities_chat_list_recyclerview.setAdapter(celebritiesChatListAdapter);
    }
    public void Initialization() {
        arrow_icon = findViewById(R.id.arrow_icon);
        cart_icon = findViewById(R.id.cart_icon);
        report_icon = findViewById(R.id.report_icon);
        celebrities_chat_list_recyclerview = findViewById(R.id.celebrities_chat_list_recyclerview);
        celebritiesChatListModels = new ArrayList<>();
        celebritiesChatListAdapter = new CelebritiesChatListAdapter(celebritiesChatListModels,this);



    }

    public void listeners() {
        arrow_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CelebritiesChatListActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CelebritiesChatListActivity.this, CartActivity.class);
                intent.putExtra("ActivityName","CelebritiesChatListActivity");
                startActivity(intent);

            }
        });
        report_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CelebritiesChatListActivity.this, ReportActivity.class);
                intent.putExtra("ActivityName","CelebritiesChatListActivity");
                startActivity(intent);

            }
        });


    }
    public void fillCelebritiesModel() {
        String url = PathUrls.baseUrl + PathUrls.getCelebritiesUrl+"?user_type=1" ;
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //get data from response and fill it in Array List
                                JSONObject obj = response.getJSONObject(i);
                                celebritiesChatListModels.add(new CelebritiesChatListModel(obj.getString("user_image"),
                                        obj.getString("name"),
                                        "hello",
                                        obj.getInt("user_id")));
                                celebritiesChatListAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CelebritiesChatListActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        requestQueue.add(jsonArrayRequest);


    }
}