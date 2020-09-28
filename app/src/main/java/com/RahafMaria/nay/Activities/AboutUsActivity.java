package com.RahafMaria.nay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.RahafMaria.nay.R;

public class AboutUsActivity extends AppCompatActivity {
    ImageView arrow_icon;
    ImageView cart_icon;
    ImageView report_icon;
    TextView title,second_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Initialization();
        listeners();

    }

    private void Initialization() {

        arrow_icon = findViewById(R.id.arrow_icon);
        title=findViewById(R.id.title);
        second_title=findViewById(R.id.the_target);
        cart_icon = findViewById(R.id.cart_icon);
        report_icon = findViewById(R.id.report_icon);

    }
    public void listeners(){
        arrow_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        cart_icon .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUsActivity.this,CartActivity.class);
                intent.putExtra("ActivityName","AboutUsActivity");
                startActivity(intent);

            }
        });
        report_icon .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUsActivity.this,ReportActivity.class);
                intent.putExtra("ActivityName","AboutUsActivity");
                startActivity(intent);

            }
        });

    }
}