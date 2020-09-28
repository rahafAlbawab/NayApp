package com.RahafMaria.nay.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText email_login;
    EditText password_login;
    Button login_button;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String permission[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permission, 1);
        }
        Initialization();
        listeners();

    }

    public void Initialization() {
        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        login_button = findViewById(R.id.login_button);
        sharedPreferences = getSharedPreferences("loginChecked", MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void listeners() {
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email_login.getText().toString().equals("")|| password_login.getText().toString().equals("")) {

                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

                }
                else if (SignUpActivity.checkEmail(email_login.getText().toString())) {
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    StringRequest sr = new StringRequest(Request.Method.POST, PathUrls.baseUrl + PathUrls.loginUrl, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.d("responseRahaf",response);
                            if (response.equals("0")) {
                                Toast.makeText(LoginActivity.this, "The password or email is wrong", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                editor.putString("user_id", response);
                                editor.commit();
                                startActivity(intent);
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.getMessage());

                        }


                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", email_login.getText().toString() + "");
                            params.put("password", password_login.getText().toString() + "");


                            return params;
                        }


                    };
                    queue.add(sr);

                    editor.putString("isLogged", "yes");
                    editor.commit();

                }
                else{
                    Toast.makeText(LoginActivity.this, "The email is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void goToSignUpButton(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You must Accept All Permissions", Toast.LENGTH_LONG).show();
            }
        }
    }
}