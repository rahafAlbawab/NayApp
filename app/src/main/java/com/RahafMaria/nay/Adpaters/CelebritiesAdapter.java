package com.RahafMaria.nay.Adpaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.RahafMaria.nay.Activities.CelebritiesActivity;
import com.RahafMaria.nay.Activities.CelebrityPageActivity;
import com.RahafMaria.nay.Models.CelebritiesModel;
import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CelebritiesAdapter extends RecyclerView.Adapter<CelebritiesAdapter.CelebritiesViewHolder> {
    ArrayList<CelebritiesModel> celebritiesModel ;
    Context context ;

    public CelebritiesAdapter(ArrayList<CelebritiesModel> celebritiesModel, Context context) {
        this.celebritiesModel = celebritiesModel;
        this.context = context;
    }

    @NonNull
    @Override
    public CelebritiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celebrities_view,parent,false);
        CelebritiesViewHolder celebritiesViewHolder = new CelebritiesViewHolder(view);

        return celebritiesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CelebritiesViewHolder holder, int position) {
        String photo = PathUrls.baseUrl+ "CelibritiesImage/"+celebritiesModel.get(position).celebritiesImage;
        Picasso.get().load(photo).into(holder.celebritiesImage);
        final int celebritiesId = celebritiesModel.get(position).celebritiesId;
        final String celebritiesName = celebritiesModel.get(position).celebritiesName;
        final String celebritiesImage = photo;
        holder.celebritiesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CelebrityPageActivity.class);
                intent.putExtra("celebritiesId",celebritiesId);
                intent.putExtra("celebritiesName",celebritiesName);
                intent.putExtra("celebritiesImage",celebritiesImage);

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return celebritiesModel.size();
    }

    public class CelebritiesViewHolder extends RecyclerView.ViewHolder {
        ImageView celebritiesImage;


        public CelebritiesViewHolder(@NonNull View itemView) {
            super(itemView);
            celebritiesImage = itemView.findViewById(R.id.celebrity_image);
        }
    }
}
