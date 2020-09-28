package com.RahafMaria.nay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.RahafMaria.nay.R;

public class ContactUsActivity extends AppCompatActivity {
    ImageView arrow_icon;
    ImageView cart_icon;
    ImageView report_icon;
    TextView contact_text,phone_text,facebook_text,instegram_text,email_text;
    ImageView whatsapp_icon,facebook_icon,instagram_icon,email_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Initialization();
        listeners();


    }
    public void Initialization() {

        arrow_icon = findViewById(R.id.arrow_icon);
        phone_text=findViewById(R.id.phpne_number);
        facebook_text=findViewById(R.id.facebook_name);
        instegram_text=findViewById(R.id.instagram_name);
        email_text=findViewById(R.id.email_text);
        whatsapp_icon=findViewById(R.id.whatsapp_icon);
        facebook_icon=findViewById(R.id.facebook_icon);
        instagram_icon=findViewById(R.id.instegram_icon);
        email_icon=findViewById(R.id.email_icon);
        cart_icon = findViewById(R.id.cart_icon);
        report_icon = findViewById(R.id.report_icon);


    }
    public void listeners(){
        arrow_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        cart_icon .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUsActivity.this,CartActivity.class);
                intent.putExtra("ActivityName","ContactUsActivity");
                startActivity(intent);

            }
        });
        report_icon .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUsActivity.this,ReportActivity.class);
                intent.putExtra("ActivityName","ContactUsActivity");
                startActivity(intent);

            }
        });

    }
}