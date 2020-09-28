package com.RahafMaria.nay.DataBase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import com.RahafMaria.nay.Activities.CartActivity;
import com.RahafMaria.nay.Activities.ReportActivity;
import com.RahafMaria.nay.Models.ReportModel;
import com.RahafMaria.nay.PathUrls;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.RahafMaria.nay.Activities.HomeActivity.order_id;

public class RemoteDatabase {
    Context context;
    ReportActivity reportActivity;
    public static ArrayList<ReportModel> reportModels;


    public RemoteDatabase(Context context) {
        this.context = context;
    }

    public void storeInOrderTable(final int product_id, final int user_id, final int quantity, final int order_id) {

        String url = PathUrls.baseUrl + PathUrls.insertOrderUrl;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST, url, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Toast.makeText(context, "Added", Toast.LENGTH_LONG).show();
                        Log.d("resstoreInOrderTable", response + "");

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id + "");
                        params.put("product_id", product_id + "");
                        params.put("quantity", quantity + "");
                        params.put("order_id", order_id + "");
                        return params;
                    }


                };
        requestQueue.add(stringRequest);

    }

    public void deleteItemTable(final int product_id, final int user_id) {

        String url = PathUrls.baseUrl + PathUrls.deleteItemUrl;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST, url, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        Log.d("resdeleteOrderTable", response + "");
                        Toast.makeText(context, response + "", Toast.LENGTH_SHORT).show();
                        CartActivity.price_total -= Double.parseDouble(response + "");

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id + "");
                        params.put("product_id", product_id + "");
                        return params;
                    }


                };
        requestQueue.add(stringRequest);

    }

    public void insertBillTable(final int user_id, final double total, final String cash_payment, final int order_id) {

        String url = PathUrls.baseUrl + PathUrls.insertBillUrl;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST, url, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        Log.d("resinsertBillTable", response + "");


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id + "");
                        params.put("total", total + "");
                        params.put("cash_payment", cash_payment + "");
                        params.put("order_id", order_id + "");
                        return params;
                    }


                };
        requestQueue.add(stringRequest);

    }

    public void deleteOrderTable(final int user_id) {

        String url = PathUrls.baseUrl + PathUrls.deleteOrderUrl;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST, url, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.d("resdeleteItemTable", response + "");

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id + "");
                        return params;
                    }


                };
        requestQueue.add(stringRequest);

    }

    public void updateStatusTable(final int order_id) {

        String url = PathUrls.baseUrl + PathUrls.updateStatusUrl;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST, url, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.d("resupdateOrderTable", response + "");

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("order_id", order_id + "");
                        return params;
                    }


                };
        requestQueue.add(stringRequest);

    }

    public void insertChatTable( final int user_sender_id, final int user_receiver_id, final String message, final int type) {
        final String chat_id = Math.min(user_sender_id,user_receiver_id)+""+Math.max(user_sender_id,user_receiver_id);
        String url = PathUrls.baseUrl + PathUrls.insertChatUrl;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST, url, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        Log.d("resinsertChatTable", response + "");


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("chat_id", chat_id + "");
                        params.put("user_sender", user_sender_id + "");
                        params.put("user_reciver", user_receiver_id + "");
                        params.put("message", message + "");
                        params.put("message_type", type + "");
                        return params;
                    }


                };
        requestQueue.add(stringRequest);


    }
}

