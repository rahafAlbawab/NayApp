package com.RahafMaria.nay.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.RahafMaria.nay.Models.CelebrityWithChatModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class LocalDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "Nay_Database";
    public static final int DB_VERSION = 2;
    public static final String CHAT = "Chat";


    public LocalDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + CHAT +
                "(chat_id integer , user_sender_id integer , user_receiver_id integer " +
                ", message text , message_send_date text ,type integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + CHAT);
        onCreate(sqLiteDatabase);

    }

    public boolean insertToTableNayDB(int user_sender_id, int user_receiver_id, String message, int type) {
        final String chat_id = Math.min(user_sender_id, user_receiver_id) + "" + Math.max(user_sender_id, user_receiver_id);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStamp = s.format(new Date());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("chat_id", chat_id);
        contentValues.put("user_sender_id", user_sender_id);
        contentValues.put("user_receiver_id", user_receiver_id);
        contentValues.put("message", message);
        contentValues.put("message_send_date", timeStamp);
        contentValues.put("type", type);
        long result = db.insert(CHAT, null, contentValues);
        return result != -1;

    }

    public ArrayList<CelebrityWithChatModel> selectToTableNayDB() {
        ArrayList<CelebrityWithChatModel> celebrityWithChatModels = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + CHAT, null);
        try {

            cursor.moveToFirst();


            while (cursor.isAfterLast() == false) {
                for (int i = 0; i < cursor.getCount(); i++) {

                    celebrityWithChatModels.add(new CelebrityWithChatModel(cursor.getInt(1), cursor.getString(3), cursor.getInt(5)));
                    cursor.moveToNext();
                }


            }
            cursor.close();
        } catch (Exception ex) {
        }
        Log.d("checkifaddCount", cursor.getCount() + "");

        return celebrityWithChatModels;
    }
}
