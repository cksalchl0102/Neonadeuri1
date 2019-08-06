package com.example.neonadeuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.neonadeuri.commomNeonaderi.*;

public class Login extends AppCompatActivity {

    //일단 이거로 설정
    String phoneNum="010-5671-3987";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.loginButton);
        TextView phoneNumber = findViewById(R.id.phoneNumberTextView);
        //phoneNum = phoneNumber.getText().toString();

        //String[] formatPhoneNumber = phoneNumber.split("-")
        //String sendPhoneNumData="";
        loginButton.setOnClickListener(loginButtonListener);
    }
    View.OnClickListener loginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //if(핸드폰 번호가 11자리 이하일 때){ 다시 입력하세요 다이얼로그}
            //if(핸드폰 번호가 존재하지 않는 번호일 때{ 가입되있지 않은 번호입니다 다이얼로그}
            Intent intent = new Intent(getApplicationContext(),Home.class);
            intent.putExtra("phoneNumber",phoneNum);
            Message.information(Login.this,"로그인 성공","로그인하셨습니다. ");
            startActivity(intent);
        }
    };
}
