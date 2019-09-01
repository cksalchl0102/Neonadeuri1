package com.example.neonadeuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.neonadeuri.commomNeonaderi.CustomTask;
import com.example.neonadeuri.commomNeonaderi.Message;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Testing extends AppCompatActivity {

    EditText phoneEdit, nameEdit, ageEdit;
    CheckBox check_Man, check_Woman;
    Button registerButton, goLoginActivityButton;
    String phoneNumber, name, age, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        phoneEdit = findViewById(R.id.testing_PhoneNumber);
        nameEdit = findViewById(R.id.testing_name);
        ageEdit = findViewById(R.id.testing_age);
        check_Man = findViewById(R.id.testing_check_man);
        check_Woman = findViewById(R.id.testing_check_woman);
        registerButton = findViewById(R.id.testing_submit);
        goLoginActivityButton = findViewById(R.id.testing_goToLogin_button);

        check_Man.setOnClickListener(Listener);
        check_Woman.setOnClickListener(Listener);
        registerButton.setOnClickListener(Listener);
        goLoginActivityButton.setOnClickListener(Listener);
    }

    View.OnClickListener Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.testing_submit:
                    phoneNumber = phoneEdit.getText().toString();
                    name = nameEdit.getText().toString();
                    age = ageEdit.getText().toString();
                    if (registerUser(phoneNumber, name, age, gender)) {
                        Message.information(getApplicationContext(), "알림", "회원가입 성공");
                    } else {

                    }
                    break;
                case R.id.testing_check_man:
                    check_Woman.setChecked(false);
                    gender = "Man";
                    break;
                case R.id.testing_check_woman:
                    check_Man.setChecked(false);
                    gender = "Woman";
                    break;
                case R.id.testing_goToLogin_button:
                    Intent intent = new Intent(Testing.this,Login.class);
                    startActivity(intent);
                    finish();
            }
        }
    };

    public boolean registerUser(String phoneNumber, String name, String age, String gender) {
        try {
            String result = new CustomTask().execute(phoneNumber, name, age, gender, "join").get();
            Log.i("chanmi", "CustomTask().execute : " + result + "OK");

            if (result.equals("joinOK")) {
                return true;
            } else if (result.equals("existentPhone")) {
                Message.information(Testing.this, "알림", "이미 존재하는 번호입니다.");
                Log.i("chanmi","이미 존재하는 전화번호입니다.");
                phoneEdit.setText("");
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}

