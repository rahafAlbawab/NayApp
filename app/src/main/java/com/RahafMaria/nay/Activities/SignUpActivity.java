package com.RahafMaria.nay.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ColorStateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.RahafMaria.nay.Models.CelebrityWithChatModel;
import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    //Deceleration Views
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String birthDate = "";
    EditText user_name_sign_up, email_sign_up, password_sign_up;
    RadioGroup gender_radio_group;
    RadioButton female_radio_button, male_radio_button;
    Button sign_up_button, user_image_button;
    ImageView user_image;
    String currentDate;
    String encodedImage = " ";
    String gender;
    Uri filePath;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Initialization();
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                birthDate = "";
                birthDate += i + "/" + i1 + "/" + i2;
                Log.d("Date", birthDate);

            }
        };
        listeners();


    }

    public void Initialization() {
        sign_up_button = findViewById(R.id.sign_up_button);
        user_image = findViewById(R.id.user_image);
        user_image_button = findViewById(R.id.user_image_button);
        user_name_sign_up = findViewById(R.id.user_name_sign_up);
        email_sign_up = findViewById(R.id.email_sign_up);
        password_sign_up = findViewById(R.id.password_sign_up);
        gender_radio_group = findViewById(R.id.gender_radio_group);
        female_radio_button = findViewById(R.id.female_radio_button);
        male_radio_button = findViewById(R.id.male_radio_button);
        currentDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        sharedPreferences = getSharedPreferences("loginChecked", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("chat");

    }

    public void listeners() {
        user_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SignUpActivity.this);
            }
        });
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to check if all fields are fill
                if (user_name_sign_up.getText().toString().equals("") || email_sign_up.getText().toString().equals("")
                        || password_sign_up.getText().toString().equals("") || gender_radio_group.getCheckedRadioButtonId() == -1
                        || birthDate.equals("")) {

                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

                } else if (birthDate.compareTo(currentDate) == 1) {

                    Toast.makeText(SignUpActivity.this, "Please change the date", Toast.LENGTH_SHORT).show();

                } else if (encodedImage.equals(" ")) {

                    Toast.makeText(SignUpActivity.this, "Please choose image", Toast.LENGTH_SHORT).show();

                } else if (!checkEmail(email_sign_up.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "The email is invalid", Toast.LENGTH_SHORT).show();
                } else {
                    if (female_radio_button.isChecked()) {
                        gender = "female";
                    } else {
                        gender = "male";
                    }

                    RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                    StringRequest sr = new StringRequest(Request.Method.POST, PathUrls.baseUrl + PathUrls.signUpUrl, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            editor.putString("user_id", response);

                            Log.d("responseRahaf", response);
                            editor.commit();
                            databaseReference.child(sharedPreferences.getString("user_id", "0"))
                                    .child("message").setValue("");
                            databaseReference.child(sharedPreferences.getString("user_id", "0"))
                                    .child("type").setValue(1);
                            databaseReference.child(sharedPreferences.getString("user_id", "0"))
                                    .child("receiver_id").setValue(0);

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
                            params.put("name", user_name_sign_up.getText().toString() + "");
                            params.put("email", email_sign_up.getText().toString() + "");
                            params.put("password", password_sign_up.getText().toString() + "");
                            params.put("gender", gender + "");
                            params.put("birth_date", birthDate + "");
                            params.put("user_image", encodedImage);
                            return params;
                        }


                    };
                    queue.add(sr);

                    editor.putString("isLogged", "yes");
                    editor.commit();
                    Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    public void pickBirthDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, AlertDialog.THEME_HOLO_DARK, mDateSetListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();


    }

    public static boolean checkEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filePath = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    user_image.setImageBitmap(bitmap);
                    imageStore(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageByte, Base64.DEFAULT);

    }

}