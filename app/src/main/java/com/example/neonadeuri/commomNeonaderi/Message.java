package com.example.neonadeuri.commomNeonaderi;

import android.app.AlertDialog;
import android.content.Context;

public class Message {
    public static void information(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        //builder.setIcon(R.drawable.ic_priority_high_black_24dp);
        builder.setPositiveButton("확인", null);
        builder.create().show();
    }
}