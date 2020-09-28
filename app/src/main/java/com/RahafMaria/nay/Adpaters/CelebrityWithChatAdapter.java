package com.RahafMaria.nay.Adpaters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.RahafMaria.nay.Models.CelebrityWithChatModel;
import com.RahafMaria.nay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CelebrityWithChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CelebrityWithChatModel> celebrityWithChatModels;
    Context context;
    int user_id;

    public CelebrityWithChatAdapter(ArrayList<CelebrityWithChatModel> celebrityWithChatModels, Context context, int user_id) {
        this.celebrityWithChatModels = celebrityWithChatModels;
        this.context = context;
        this.user_id = user_id;
    }

    @Override
    public int getItemViewType(int position) {
        if(celebrityWithChatModels.get(position).user_id == user_id){
            if(celebrityWithChatModels.get(position).type == 1){
                return 1;
            }
            else {
                return 2;
            }
        }
        else{
            if(celebrityWithChatModels.get(position).type == 1){
                return 3;
            }
            else {
                return 4;
            }
        }
    }

    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_sent, parent, false);
            TextSendHolder textSendHolder = new TextSendHolder(view);
            return textSendHolder;
        } else if (viewType == 3) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_recive, parent, false);
            TextReciverHolder textReciverHolder = new TextReciverHolder(view);
            return textReciverHolder;
        } else if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_sent, parent, false);
            ImageSendHolder imageSendHolder = new ImageSendHolder(view);
            return imageSendHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_receive, parent, false);
            ImageReciverHolder imageReciverHolder = new ImageReciverHolder(view);
            return imageReciverHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(celebrityWithChatModels.get(position).user_id  == user_id){
            if(celebrityWithChatModels.get(position).type == 1){
                ((TextSendHolder) holder).text_sent.setText(celebrityWithChatModels.get(position).message);

            }
            else{

                Picasso.get().load(celebrityWithChatModels.get(position).message).into(((ImageSendHolder) holder).image_sent);
            }
        }
        else {
            if(celebrityWithChatModels.get(position).type == 1){

                ((TextReciverHolder) holder).text_receive.setText(celebrityWithChatModels.get(position).message);

            }
            else{
                Picasso.get().load(celebrityWithChatModels.get(position).message).into(((ImageReciverHolder) holder).image_recive);
            }

        }

    }

    @Override
    public int getItemCount() {
        return celebrityWithChatModels.size();
    }

    public class TextSendHolder extends RecyclerView.ViewHolder {
        TextView text_sent;

        public TextSendHolder(@NonNull View itemView) {
            super(itemView);
            text_sent = (TextView) itemView.findViewById(R.id.text_sent);
        }


    }

    public class TextReciverHolder extends RecyclerView.ViewHolder {
        TextView text_receive;

        public TextReciverHolder(@NonNull View itemView) {
            super(itemView);
            text_receive = (TextView)itemView.findViewById(R.id.text_receive);
        }
    }

    public class ImageSendHolder extends RecyclerView.ViewHolder {
        ImageView image_sent;

        public ImageSendHolder(@NonNull View itemView) {
            super(itemView);
            image_sent =(ImageView) itemView.findViewById(R.id.image_sent);
        }
    }

    public class ImageReciverHolder extends RecyclerView.ViewHolder {
        ImageView image_recive;

        public ImageReciverHolder(@NonNull View itemView) {
            super(itemView);
            image_recive = (ImageView)itemView.findViewById(R.id.image_recive);
        }
    }
}
