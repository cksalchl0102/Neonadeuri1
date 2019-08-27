package com.example.neonadeuri.commomNeonaderi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

import com.example.neonadeuri.R;


public class Message {
    private static String info;
    public static void information(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("확인", null);
        builder.create().show();
    }
}
