package com.example.neonadeuri.commomNeonaderi;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class CustomTask extends AsyncTask<String, Void, String> {
    private String sendMsg, recieveMsg;

    @Override
    protected String doInBackground(String... strings) {
        String jspUrl = "http://192.168.0.14:8080/study_v1/neonaduriAdd.jsp";
        //랩실 113.198.84.24
        //집 : 192.168.219.100
        //thisgs : 192.168.0.15:5555
        HttpURLConnection conn = null;

        try {
            String tmp;
            URL url = new URL(jspUrl);

            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            sendMsg = "phoneNumber=" + strings[0] + "&name=" + strings[1] + "&age=" + strings[2] + "&gender=" + strings[3] + "&type=" + strings[4];
            Log.i("chanmi", "보낼 값: " + strings[0] + ", " + strings[1] + ", " + strings[2] + ", " + strings[3]);
            Log.i("chanmi", "sencMsg = " + sendMsg);

            osw.write(sendMsg);
            Log.i("chanmi", "osw.write(sendMsg) 성공");
            osw.flush();

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer buffer = new StringBuffer();

                while ((tmp = reader.readLine()) != null) {
                    buffer.append(tmp);
                }
                recieveMsg = buffer.toString();
            } else {
                Log.i("chanmi", "통신 에러입니당");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
            Log.e("conn_disconnect", "disconnect");
        }
        Log.i("chanmi", "Login recieveMsg = " + recieveMsg);
        return recieveMsg;
    }
}
