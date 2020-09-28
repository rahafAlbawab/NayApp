package com.RahafMaria.nay.Adpaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.RahafMaria.nay.Activities.CelebritiesWithChatActivity;
import com.RahafMaria.nay.Activities.CelebrityPageActivity;
import com.RahafMaria.nay.Models.CelebritiesChatListModel;
import com.RahafMaria.nay.PathUrls;
import com.RahafMaria.nay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CelebritiesChatListAdapter extends RecyclerView.Adapter<CelebritiesChatListAdapter.CelebritiesChatListViewHolder> {
    ArrayList<CelebritiesChatListModel> celebritiesChatListModels;
    Context context;

    public CelebritiesChatListAdapter(ArrayList<CelebritiesChatListModel> celebritiesChatListModels, Context context) {
        this.celebritiesChatListModels = celebritiesChatListModels;
        this.context = context;
    }

    @NonNull
    @Override
    public CelebritiesChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celebrities_chat_list_view,parent,false);
        CelebritiesChatListViewHolder celebritiesChatListViewHolder = new CelebritiesChatListViewHolder(view);
        return  celebritiesChatListViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CelebritiesChatListViewHolder holder, int position) {

        String photo = PathUrls.baseUrl+ "UserImage/"+celebritiesChatListModels.get(position).celebritiesImage;
        Picasso.get().load(photo).into(holder.celebrity_image);
        holder.celebrity_name.setText(celebritiesChatListModels.get(position).celebritiesName);
        holder.last_message.setText(celebritiesChatListModels.get(position).lastMessage);
        final int celebrity_id = celebritiesChatListModels.get(position).celebritiesId;
        final String celebrity_name = celebritiesChatListModels.get(position).celebritiesName;
        final String celebrity_image = photo;

        holder.celebrity_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CelebritiesWithChatActivity.class);
                intent.putExtra("celebrity_id",celebrity_id);
                intent.putExtra("celebrity_name",celebrity_name);
                intent.putExtra("celebrity_image",celebrity_image);


                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return celebritiesChatListModels.size();
    }

    public class CelebritiesChatListViewHolder extends RecyclerView.ViewHolder {
        ImageView celebrity_image;
        TextView celebrity_name, last_message;
        LinearLayout celebrity_information;

        public CelebritiesChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            celebrity_image = itemView.findViewById(R.id.celebrity_image);
            celebrity_name = itemView.findViewById(R.id.celebrity_name);
            last_message = itemView.findViewById(R.id.last_message);
            celebrity_information = itemView.findViewById(R.id.celebrity_information);
        }
    }
}
