package com.RahafMaria.nay.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.RahafMaria.nay.Adpaters.CartAdapter;
import com.RahafMaria.nay.DataBase.RemoteDatabase;
import com.RahafMaria.nay.Models.CartModel;
import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;


public class CartActivity extends AppCompatActivity {
    ImageView arrow_icon;
    ImageView report_icon;
    public static TextView total_price;
    TextView paypal_text, cash_text;
    Button confirm_button, quantity_button, quantity_cancel_button;
    RecyclerView cart_recyclview;
    Drawable boreder_background ;
    CartAdapter cartAdapter;
    ArrayList<CartModel> cartModels;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static Double price_total;
    public static String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    public static String CONFIG_CLIENT_ID = "ARDsAYKaKnX7uAyX6CBHNxMqQQoEbd6zK4c3zvAFsDnQ2AkA7anCO3RZTUskFCLu7T5g23rKFNurR7vq";
    public String payment_method;
    private static int REQUEST_CODE_PAYMENT = 1;
    public static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);

    String paypal_id, paypal_state, paypal_amount, paypal_currency_code;
    RemoteDatabase remoteDatabase;
    Intent intent;
    Intent intentBack;
    int order_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Initialization();
        listeners();
        cart_recyclview.setLayoutManager(new LinearLayoutManager(this));
        fillCartModel(Integer.parseInt(sharedPreferences.getString("user_id", "0")));
        cart_recyclview.setAdapter(cartAdapter);


    }

    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    public void fillCartModel(int user_id) {
        //first step get url of page that fetches the data from DB
        // مابعد ال ? هو الشرط اللي حنمرره لل Query
        String url = PathUrls.baseUrl + PathUrls.getOrderUrl + "?user_id=" + user_id;
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //get data from response and fill it in Array List
                                JSONObject obj = response.getJSONObject(i);
                                double sub_total = obj.getInt("quantity") * obj.getDouble("price");
                                price_total += sub_total;
                                cartModels.add(new CartModel(obj.getInt("quantity"),
                                        obj.getInt("product_id"),
                                        obj.getString("name"),
                                        obj.getString("image"),
                                        sub_total));
                                cartAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        total_price.setText(price_total + "JD");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CartActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        requestQueue.add(jsonArrayRequest);


    }

    public void Initialization() {
        arrow_icon = findViewById(R.id.arrow_icon);
        report_icon = findViewById(R.id.report_icon);
        confirm_button = findViewById(R.id.confirm_button);
        cart_recyclview = findViewById(R.id.cart_recyclerview);
        intentBack = getIntent();
        cartModels = new ArrayList<>();
        cartAdapter = new CartAdapter(cartModels);
        sharedPreferences = getSharedPreferences("loginChecked", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        total_price = findViewById(R.id.total_price);
        price_total = 0.0;
        remoteDatabase = new RemoteDatabase(this);
        intent = new Intent(CartActivity.this, HomeActivity.class);
        order_id = Integer.parseInt(sharedPreferences.getString("order_id","0"));





    }

    public void listeners() {
        arrow_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(CartActivity.this, HomeActivity.class);
                if (intentBack.getStringExtra("ActivityName") != null){
                    if (intentBack.getStringExtra("ActivityName").equals("AboutUsActivity")){
                        intent = new Intent(CartActivity.this, AboutUsActivity.class);
                    }
                    else if (intentBack.getStringExtra("ActivityName").equals("ContactUsActivity")){
                        intent = new Intent(CartActivity.this, ContactUsActivity.class);
                    }
                    else if (intentBack.getStringExtra("ActivityName").equals("CelebritiesActivity")){
                        intent = new Intent(CartActivity.this, CelebritiesActivity.class);
                    }
                    else if (intentBack.getStringExtra("ActivityName").equals("CelebrityPageActivity")){
                        intent = new Intent(CartActivity.this, CelebrityPageActivity.class);
                    }
                    else if (intentBack.getStringExtra("ActivityName").equals("CelebritiesChatListActivity")){
                        intent = new Intent(CartActivity.this, CelebritiesChatListActivity.class);
                    }
                    else if (intentBack.getStringExtra("ActivityName").equals("HomeActivity")){
                        intent = new Intent(CartActivity.this, HomeActivity.class);
                    }
                }

                startActivity(intent);
            }
        });
        report_icon .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this,ReportActivity.class);
                startActivity(intent);

            }
        });
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boreder_background= getResources().getDrawable(R.drawable.cart_border);
                final Dialog dialog = new Dialog(CartActivity.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.choose_payment_method_dialog);
                quantity_button = (Button) dialog.findViewById(R.id.quantity_button);
                paypal_text = (TextView) dialog.findViewById(R.id.paypal_text);
                cash_text = (TextView) dialog.findViewById(R.id.cash_text);
                quantity_cancel_button = (Button) dialog.findViewById(R.id.quantity_cancel_button);
                paypal_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        payment_method = "paypal";
                        paypal_text.setBackground(boreder_background);
                        cash_text.setBackgroundColor(getResources().getColor(R.color.White));
                    }
                });
                cash_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        payment_method = "cash";
                        cash_text.setBackground(boreder_background);
                        paypal_text.setBackgroundColor(getResources().getColor(R.color.White));
                    }
                });
                quantity_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (payment_method.equalsIgnoreCase("paypal")) {
                            remoteDatabase.insertBillTable(Integer.parseInt(sharedPreferences.getString("user_id", "0")), price_total, "paypal", HomeActivity.order_id);
                            onConfirmPressed();
                            dialog.dismiss();
                        } else if(payment_method.equalsIgnoreCase("cash")) {

                            remoteDatabase.insertBillTable(Integer.parseInt(sharedPreferences.getString("user_id", "0")), price_total, "cash", HomeActivity.order_id);
                            remoteDatabase.deleteOrderTable(Integer.parseInt(sharedPreferences.getString("user_id", "0")));
                            remoteDatabase.updateStatusTable(HomeActivity.order_id);
                            HomeActivity.order_id++;
                            editor.putString("order_id",HomeActivity.order_id+"");
                            editor.commit();
                            dialog.dismiss();
                            Toast.makeText(CartActivity.this, "Thank You", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(CartActivity.this,"You must choose one of payment metod or click cancel",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                quantity_cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    public void onConfirmPressed() {
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        String total = price_total.toString();
        return new PayPalPayment(new BigDecimal(total), "USD", "Nay App",
                paymentIntent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        remoteDatabase.deleteOrderTable(Integer.parseInt(sharedPreferences.getString("user_id", "0")));
                        remoteDatabase.updateStatusTable(Integer.parseInt(sharedPreferences.getString("order_id","0")));
                        HomeActivity.order_id++;
                        editor.putString("order_id",HomeActivity.order_id+"");
                        editor.commit();
                        Toast.makeText(CartActivity.this, "Thank You", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        Log.e("Show", confirm.toJSONObject().toString(4));
                        Log.e("Show", confirm.getPayment().toJSONObject().toString(4));

                        JSONObject json = confirm.toJSONObject();
                        JSONObject responce = json.getJSONObject("response");
                        paypal_id = responce.getString("id");
                        paypal_state = responce.getString("state");
                        JSONObject payment = confirm.getPayment().toJSONObject();
                        paypal_amount = payment.getString("amount");
                        paypal_currency_code = payment.getString("currency_code");

                        Toast.makeText(getApplicationContext(), "PaymentConfirmation info received" + " from PayPal", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "an extremely unlikely failure" +
                                " occurred:", Toast.LENGTH_LONG).show();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("cancel", "Cancelled");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

                Toast.makeText(getApplicationContext(), "An invalid Payment or PayPalConfiguration" +
                        " was submitted. Please see the docs.", Toast.LENGTH_LONG).show();
            }
        }
    }
}