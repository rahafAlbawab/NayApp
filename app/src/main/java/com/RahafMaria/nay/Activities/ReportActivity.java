package com.RahafMaria.nay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.RahafMaria.nay.DataBase.RemoteDatabase;
import com.RahafMaria.nay.Models.ReportModel;
import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    ImageView arrow_icon;
    ImageView cart_icon;
    RemoteDatabase remoteDatabase;
    ArrayList<ReportModel> reportModels;
    SharedPreferences sharedPreferences;
    Intent intent;
    Intent intentBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Initialization();
        listeners();
        String url = PathUrls.baseUrl + PathUrls.getReportUrl + "?user_id=" + sharedPreferences.getString("user_id", "0");
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("resgetReportTable", response + "");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject x = response.getJSONObject(i);
                        reportModels.add(new ReportModel(x.getString("bill_date"), x.getString("total")));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                showReport();
                Log.d("resgetReportTable", reportModels.size() + "");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReportActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);


    }



    private void Initialization() {

        arrow_icon = findViewById(R.id.arrow_icon);
        cart_icon = findViewById(R.id.cart_icon);
        intent = getIntent();
        intentBack = getIntent();
        remoteDatabase = new RemoteDatabase(this);
        reportModels = new ArrayList<>();
        sharedPreferences = getSharedPreferences("loginChecked", MODE_PRIVATE);


    }

    public void listeners() {
        arrow_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (intentBack.getStringExtra("ActivityName") != null){
                    if (intentBack.getStringExtra("ActivityName").equals("AboutUsActivity")){
                        intent = new Intent(ReportActivity.this, AboutUsActivity.class);
                    }
                    else if (intentBack.getStringExtra("ActivityName").equals("ContactUsActivity")){
                        intent = new Intent(ReportActivity.this, ContactUsActivity.class);
                    }
                    else if (intentBack.getStringExtra("ActivityName").equals("CelebritiesActivity")){
                        intent = new Intent(ReportActivity.this, CelebritiesActivity.class);
                    }
                    else if (intentBack.getStringExtra("ActivityName").equals("CelebrityPageActivity")){
                        intent = new Intent(ReportActivity.this, CelebrityPageActivity.class);
                    }
                    else if (intentBack.getStringExtra("ActivityName").equals("CelebritiesChatListActivity")){
                        intent = new Intent(ReportActivity.this, CelebritiesChatListActivity.class);
                    }
                    else if (intentBack.getStringExtra("ActivityName").equals("HomeActivity")){
                        intent = new Intent(ReportActivity.this, HomeActivity.class);
                    }

                }

                startActivity(intent);
            }
        });
        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportActivity.this, CartActivity.class);
                startActivity(intent);

            }
        });


    }

    public void showReport() {
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        Cartesian cartesian = AnyChart.column();
        List<DataEntry> data = new ArrayList<>();
        for (int i = 0; i < reportModels.size(); i++) {
            Log.d("counter",i+"");
            data.add(new ValueDataEntry(reportModels.get(i).bill_date.substring(0,10), Integer.parseInt(reportModels.get(i).bill_total)));
        }
        Column column = cartesian.column(data);
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("History of my purchases");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Date");
        cartesian.yAxis(0).title("Salary");
        anyChartView.setBackgroundColor("#450224");

      column.color("#450224");
        anyChartView.setChart(cartesian);
    }
}