package com.RahafMaria.nay.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.RahafMaria.nay.Adpaters.ArmaniAdapter;
import com.RahafMaria.nay.Adpaters.HairAdapter;
import com.RahafMaria.nay.Models.ArmaniModel;
import com.RahafMaria.nay.Models.HairModels;
import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.android.volley.Request.Method.GET;


public class ArmaniFragment extends Fragment {
    RecyclerView armani_recyclerView;
    ArrayList<ArmaniModel> armaniModel;
    ArmaniAdapter armaniAdapter;
    Context context;


    public ArmaniFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_armani, container, false);
        context = container.getContext();
        armani_recyclerView = view.findViewById(R.id.armani_recyclerview);
        armaniModel = new ArrayList<>();
        armaniAdapter = new ArmaniAdapter(armaniModel);
        armani_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fillList();

        armani_recyclerView.setAdapter(armaniAdapter);

        return view;
    }

    private void fillList() {
        //first step get url of page that fetches the data from DB
        // مابعد ال ? هو الشرط اللي حنمرره لل Query
        String url = PathUrls.baseUrl + PathUrls.getProductsUrl + "?category=armani";
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //get data from response and fill it in Array List
                                JSONObject obj = response.getJSONObject(i);
                                armaniModel.add(new ArmaniModel(obj.getString("image"),
                                        obj.getString("name"),
                                        obj.getInt("price"),
                                        obj.getInt("product_id")));
                                armaniAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        requestQueue.add(jsonArrayRequest);

    }
}