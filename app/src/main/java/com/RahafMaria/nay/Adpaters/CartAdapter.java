package com.RahafMaria.nay.Adpaters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.RahafMaria.nay.Activities.CartActivity;
import com.RahafMaria.nay.Activities.HomeActivity;
import com.RahafMaria.nay.DataBase.RemoteDatabase;
import com.RahafMaria.nay.Models.CartModel;
import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    ArrayList<CartModel> cartModels;
    Context context = HomeActivity.context;
    CartActivity cartActivity;
    RemoteDatabase remoteDatabase = new RemoteDatabase(context);
    SharedPreferences sharedPreferences = context.getSharedPreferences("loginChecked", Context.MODE_PRIVATE);
    public CartAdapter(ArrayList<CartModel> cartModels) {
        this.cartModels = cartModels;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view,parent,false);
        CartViewHolder cartViewHolder = new CartViewHolder(view);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        String photo = PathUrls.baseUrl+ "Images/"+cartModels.get(position).product_image;
        Picasso.get().load(photo).into(holder.product_image);
        holder.product_name.setText(cartModels.get(position).product_name+"");
        holder.product_price.setText(cartModels.get(position).product_price+"");
        holder.product_quantity.setText(cartModels.get(position).product_quantity+"");
        holder.product_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remoteDatabase.deleteItemTable(cartModels.get(position).product_id,
                        Integer.parseInt(sharedPreferences.getString("user_id", "0")
                        ));
                CartActivity.price_total-=cartModels.get(position).product_price;
                Log.d("reduce",CartActivity.price_total+"");
                CartActivity.total_price.setText(CartActivity.price_total+"");
                cartModels.remove(position);
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView product_image,product_delete;
        TextView product_name,product_price,product_quantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_quantity = itemView.findViewById(R.id.product_quantity);
            product_delete = itemView.findViewById(R.id.product_delete);
        }
    }
}
