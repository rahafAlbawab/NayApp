package com.RahafMaria.nay.Models;

public class CelebrityWithChatModel {
    public int user_id;
    public String message;
    public int type;

    public CelebrityWithChatModel(int user_id, String message, int type) {
        this.user_id = user_id;
        this.message = message;
        this.type = type;
    }
}
