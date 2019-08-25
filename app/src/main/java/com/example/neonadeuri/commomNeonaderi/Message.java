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
        final EditText editText = new EditText(context);
        builder.setView(editText);
        //builder.setIcon(R.drawable.ic_priority_high_black_24dp);
        builder.setPositiveButton("확인", null);
        builder.create().show();
    }/*
    public static void infoGetString(final Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(message);
        final EditText editText = new EditText(context);
        builder.setView(editText);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                info = editText.getText().toString();
                Toast.makeText(context,info,Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("취소",null);
    }*/
}
