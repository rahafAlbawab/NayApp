package com.RahafMaria.nay.Models;

public class CelebritiesChatListModel {
    public String celebritiesImage ,celebritiesName ,lastMessage;
    public  int celebritiesId;

    public CelebritiesChatListModel(String celebritiesImage, String celebritiesName, String lastMessage, int celebritiesId) {
        this.celebritiesImage = celebritiesImage;
        this.celebritiesName = celebritiesName;
        this.lastMessage = lastMessage;
        this.celebritiesId = celebritiesId;
    }
}
