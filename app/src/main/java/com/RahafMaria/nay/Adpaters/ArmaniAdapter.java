package com.RahafMaria.nay.Adpaters;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.RahafMaria.nay.Activities.HomeActivity;
import com.RahafMaria.nay.DataBase.RemoteDatabase;
import com.RahafMaria.nay.Models.ArmaniModel;
import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArmaniAdapter extends RecyclerView.Adapter<ArmaniAdapter.ArmaniViewHolder> {
    ArrayList<ArmaniModel> armaniModel;
    Context context = HomeActivity.context;
    Button quantity_button;
    Button quantity_cancel_button;
    TextView quantity;
    RemoteDatabase remoteDatabase = new RemoteDatabase(context);
    SharedPreferences sharedPreferences = context.getSharedPreferences("loginChecked",Context.MODE_PRIVATE);
    public ArmaniAdapter(ArrayList<ArmaniModel> armaniModel) {
        this.armaniModel = armaniModel;
    }

    @NonNull
    @Override
    public ArmaniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.armani_view, parent, false);
        ArmaniViewHolder armaniViewHolder = new ArmaniViewHolder(view);
        return armaniViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ArmaniViewHolder holder, final int position) {
        // 1- To get image from server by link :https://beautycosmeticsapp.000webhostapp.com/Images/armani.jpg
        String photo = PathUrls.baseUrl+ "Images/"+armaniModel.get(position).product_image;
        //2- to Display image come from internet should use picasso
        //photo -> link of image
        //holder.product_image -> The location where the image is displayed
        Picasso.get().load(photo).into(holder.product_image);
        holder.product_price.setText(armaniModel.get(position).product_price+"");
        holder.product_name.setText(armaniModel.get(position).product_name);
        holder.add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("user_id", sharedPreferences.getString("user_id", "0")+"");
                final Dialog dialog = new Dialog(context);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.add_item_layout);
                quantity_button = (Button)dialog.findViewById(R.id.quantity_button);
                quantity_cancel_button = (Button)dialog.findViewById(R.id.quantity_cancel_button);
                quantity =(TextView)dialog.findViewById(R.id.product_quantity);
                quantity_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(quantity.getText().toString().equals("")){
                            Toast.makeText(context, "You must enter a quantity for item or click cancel ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            remoteDatabase.storeInOrderTable(armaniModel.get(position).product_id,
                                    Integer.parseInt(sharedPreferences.getString("user_id", "0")),
                                    Integer.parseInt(quantity.getText().toString()),
                                    HomeActivity.order_id);
                            dialog.dismiss();
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

    @Override
    public int getItemCount() {
        return armaniModel.size();
    }

    public class ArmaniViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, product_price;
        ImageView product_image , add_image;


        public ArmaniViewHolder(@NonNull View v) {
            super(v);
            product_image = v.findViewById(R.id.product_image);
            product_name = v.findViewById(R.id.product_name);
            product_price = v.findViewById(R.id.product_price);
            add_image = v.findViewById(R.id.add_button);


        }

    }

}
