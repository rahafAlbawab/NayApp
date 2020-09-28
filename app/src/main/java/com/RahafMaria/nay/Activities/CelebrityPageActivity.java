package com.RahafMaria.nay.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.RahafMaria.nay.Adpaters.CartAdapter;
import com.RahafMaria.nay.Adpaters.FavoriteProductAdapter;
import com.RahafMaria.nay.Models.ArmaniModel;
import com.RahafMaria.nay.Models.CartModel;
import com.RahafMaria.nay.Models.FavoriteProductModel;
import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CelebrityPageActivity extends AppCompatActivity {
    ImageView arrow_icon ,celebrity_image;
    ImageView cart_icon;
    ImageView report_icon;
    TextView celebrity_name;
    public static Context context;
    RecyclerView favorite_product_recyclerview;
    FavoriteProductAdapter favoriteProductAdapter;
    ArrayList<FavoriteProductModel> favoriteProductModels;
    Intent intent;
    String celebritiesName;
    String celebritiesImage;
    int celebritiesId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity_page);
        Initialization();
        listeners();
        if (intent.getStringExtra("celebritiesImage") != null
                && intent.getStringExtra("celebritiesName") != null
                && intent.getIntExtra("celebritiesId", 0) != 0) {
            celebritiesName = intent.getStringExtra("celebritiesName");
            celebritiesImage = intent.getStringExtra("celebritiesImage");
            celebritiesId = intent.getIntExtra("celebritiesId", 0);
            Picasso.get().load(celebritiesImage).into(celebrity_image);
            celebrity_name.setText(celebritiesName);
        }

        favorite_product_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        fillModel();
        favorite_product_recyclerview.setAdapter(favoriteProductAdapter);

    }

    private void fillModel() {
        String url = PathUrls.baseUrl + PathUrls.getfavorateProductUrl + "?celebrities_id="+celebritiesId;
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //get data from response and fill it in Array List
                                JSONObject obj = response.getJSONObject(i);
                                favoriteProductModels.add(new FavoriteProductModel(
                                        obj.getString("image"),
                                        obj.getString("name"),
                                        obj.getInt("price"),
                                        obj.getInt("product_id")
                                ));
                                favoriteProductAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CelebrityPageActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);


    }

    public void Initialization() {

        arrow_icon = findViewById(R.id.arrow_icon);
        context = CelebrityPageActivity.this;
        favorite_product_recyclerview = findViewById(R.id.favorite_product_recyclerview);
        favoriteProductModels = new ArrayList<>();
        favoriteProductAdapter = new FavoriteProductAdapter(favoriteProductModels);
        intent = getIntent();
        celebrity_image = findViewById(R.id.celebrity_image);
        celebrity_name  = findViewById(R.id.celebrity_name);
        cart_icon = findViewById(R.id.cart_icon);
        report_icon = findViewById(R.id.report_icon);

    }

    public void listeners() {
        arrow_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CelebrityPageActivity.this, CelebritiesActivity.class);
                startActivity(intent);
            }
        });
        cart_icon .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CelebrityPageActivity.this,CartActivity.class);
                intent.putExtra("ActivityName","CelebritiesActivity");
                startActivity(intent);

            }
        });
        report_icon .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CelebrityPageActivity.this,ReportActivity.class);
                intent.putExtra("ActivityName","CelebritiesActivity");
                startActivity(intent);

            }
        });

    }
}