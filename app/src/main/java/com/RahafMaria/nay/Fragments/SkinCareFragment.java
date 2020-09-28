package com.RahafMaria.nay.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.RahafMaria.nay.Adpaters.HairAdapter;
import com.RahafMaria.nay.Adpaters.SkinAdapter;
import com.RahafMaria.nay.Models.ArmaniModel;
import com.RahafMaria.nay.Models.HairModels;
import com.RahafMaria.nay.Models.SkinModel;
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


public class SkinCareFragment extends Fragment {
    RecyclerView skin_recyclerView;
    ArrayList<SkinModel> skinModel;
    SkinAdapter skinAdapter;

    public SkinCareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skin_care, container, false);
        skin_recyclerView = view.findViewById(R.id.skin_recyclerview);

        skinModel = new ArrayList<>();

        skinAdapter = new SkinAdapter(skinModel);
        skin_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fillList();
        skin_recyclerView.setAdapter(skinAdapter);



        return view;
    }
    private void fillList() {
        String url = PathUrls.baseUrl + PathUrls.getProductsUrl + "?category=skin";
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                skinModel.add(new SkinModel(obj.getString("image"),
                                        obj.getString("name"),
                                        obj.getInt("price"),
                                        obj.getInt("product_id")));
                                skinAdapter.notifyDataSetChanged();
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