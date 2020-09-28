package com.RahafMaria.nay.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.RahafMaria.nay.Adpaters.CelebrityWithChatAdapter;
import com.RahafMaria.nay.DataBase.LocalDatabase;
import com.RahafMaria.nay.DataBase.RemoteDatabase;
import com.RahafMaria.nay.Models.CelebrityWithChatModel;
import com.RahafMaria.nay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;

public class CelebritiesWithChatActivity extends AppCompatActivity {
    ImageView arrow_icon, camera_chat, send_chat, celebrity_image;
    RecyclerView chat_recycler;
    EditText chat_text;
    TextView celebrity_name;
    Uri filePath;
    CelebrityWithChatAdapter celebrityWithChatAdapter;
    ArrayList<CelebrityWithChatModel> celebrityWithChatModels;
    SharedPreferences sharedPreferences;
    RemoteDatabase remoteDatabase;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    LocalDatabase localDatabase;
    Intent intent;
    String celebritiesName;
    String celebritiesImage;
    public static int celebritiesId;
    String last_message_receive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrities_with_chat);
        Initialization();
        listeners();
        if (intent.getStringExtra("celebrity_name") != null
                && intent.getStringExtra("celebrity_image") != null
                && intent.getIntExtra("celebrity_id", 0) != 0) {
            celebritiesName = intent.getStringExtra("celebrity_name");
            celebritiesImage = intent.getStringExtra("celebrity_image");
            celebritiesId = intent.getIntExtra("celebrity_id", 0);
            Picasso.get().load(celebritiesImage).into(celebrity_image);
            celebrity_name.setText(celebritiesName);

        }
        chat_recycler.setLayoutManager(new LinearLayoutManager(this));
        celebrityWithChatModels.addAll(localDatabase.selectToTableNayDB());
        chat_recycler.setAdapter(celebrityWithChatAdapter);
        chat_recycler.scrollToPosition(celebrityWithChatModels.size() - 1);
        for (int i = celebrityWithChatModels.size() - 1; i > 0; i--) {
            if (celebrityWithChatModels.get(i).user_id != Integer.parseInt(sharedPreferences.getString("user_id", "0"))) {
                last_message_receive = celebrityWithChatModels.get(i).message;
                return;
            }

        }

        databaseReference.child(celebritiesId + "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("message").getValue().toString().equals("")) {
                    if (dataSnapshot.child("receiver_id").getValue().toString().equals(sharedPreferences.getString("user_id", "0"))) {
                        if ((celebrityWithChatModels.size() != 0) &&
                                (!dataSnapshot.child("message").getValue().toString().equals(celebrityWithChatModels.get(celebrityWithChatModels.size() - 1).message))) {
                            // 1- add to Array List
                            celebrityWithChatModels.add(new CelebrityWithChatModel(celebritiesId,
                                    dataSnapshot.child("message").getValue().toString(),
                                    Integer.parseInt(dataSnapshot.child("type").getValue().toString())));
                            celebrityWithChatAdapter.notifyDataSetChanged();
                            chat_recycler.scrollToPosition(celebrityWithChatModels.size() - 1);
                            //2- add to local DB
                            localDatabase.insertToTableNayDB(celebritiesId,
                                    Integer.parseInt(sharedPreferences.getString("user_id", "0")),
                                    dataSnapshot.child("message").getValue().toString(),
                                    Integer.parseInt(dataSnapshot.child("type").getValue().toString()));


                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void Initialization() {
        arrow_icon = findViewById(R.id.arrow_icon);
        celebrity_image = findViewById(R.id.celebrity_image);
        celebrity_name = findViewById(R.id.celebrity_name);
        chat_text = findViewById(R.id.chat_text);
        chat_recycler = findViewById(R.id.chat_recycler);
        camera_chat = findViewById(R.id.camera_chat);
        send_chat = findViewById(R.id.send_chat);
        localDatabase = new LocalDatabase(this);
        remoteDatabase = new RemoteDatabase(this);
        intent = getIntent();
        sharedPreferences = getSharedPreferences("loginChecked", MODE_PRIVATE);
        celebrityWithChatModels = new ArrayList<>();
        celebrityWithChatAdapter = new CelebrityWithChatAdapter(celebrityWithChatModels, this, Integer.parseInt(sharedPreferences.getString("user_id", "0")));
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = firebaseDatabase.getReference("chat");


    }

    public void listeners() {
        arrow_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CelebritiesWithChatActivity.this, CelebritiesChatListActivity.class);
                startActivity(intent);
            }
        });

        send_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chat_text.getText().toString().equals("")) {
                    //1-Store in Firebase Database

                    databaseReference.child(sharedPreferences.getString("user_id", "0"))
                            .child("message").setValue(chat_text.getText().toString());
                    databaseReference.child(sharedPreferences.getString("user_id", "0"))
                            .child("type").setValue(1);
                    databaseReference.child(sharedPreferences.getString("user_id", "0"))
                            .child("receiver_id").setValue(celebritiesId);
                    //2- Store in Remote Database
                    remoteDatabase.insertChatTable(Integer.parseInt(sharedPreferences.getString("user_id", "0")),
                            celebritiesId,
                            chat_text.getText().toString(),
                            1);

                    //3- store in Local Database
                    boolean check = localDatabase.insertToTableNayDB(Integer.parseInt(sharedPreferences.getString("user_id", "0")),
                            celebritiesId,
                            chat_text.getText().toString(),
                            1);
                    Log.d("check", check + "");

                    //4- store in ArrayList

                    celebrityWithChatModels.add(new CelebrityWithChatModel(Integer.parseInt(sharedPreferences.getString("user_id", "0")),
                            chat_text.getText().toString(), 1));

                    celebrityWithChatAdapter.notifyDataSetChanged();
                    chat_recycler.scrollToPosition(celebrityWithChatModels.size() - 1);

                    chat_text.setText("");

                }
            }
        });

        camera_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(CelebritiesWithChatActivity.this);


            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filePath = result.getUri();

                String name = databaseReference.push().getKey();
                final StorageReference ref = storageReference.child("Images/" + name);
                ref.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //4- Store in ArrayList

                                    celebrityWithChatModels.add(new CelebrityWithChatModel(Integer.parseInt(sharedPreferences.getString("user_id", "0")),
                                            uri.toString(), 2));

                                    celebrityWithChatAdapter.notifyDataSetChanged();
                                    chat_recycler.scrollToPosition(celebrityWithChatModels.size() - 1);

                                    //1-store in RealTime DB
                                    databaseReference.child(sharedPreferences.getString("user_id", "0"))
                                            .child("message").setValue(uri.toString());
                                    databaseReference.child(sharedPreferences.getString("user_id", "0"))
                                            .child("type").setValue(2);
                                    databaseReference.child(sharedPreferences.getString("user_id", "0"))
                                            .child("receiver_id").setValue(celebritiesId);
                                    //2- Store in Remote Database
                                    remoteDatabase.insertChatTable(Integer.parseInt(sharedPreferences.getString("user_id", "0")),
                                            celebritiesId,
                                            uri.toString(),
                                            2);

                                    //3- store in Local Database
                                    localDatabase.insertToTableNayDB(Integer.parseInt(sharedPreferences.getString("user_id", "0")),
                                            celebritiesId,
                                            uri.toString(),
                                            2);


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                        } else {
                            Toast.makeText(CelebritiesWithChatActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}