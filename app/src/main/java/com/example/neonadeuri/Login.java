package com.example.neonadeuri;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neonadeuri.commomNeonaderi.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    //이벤트 사용자의 번호 입력.
    TextView phoneNumberTextView;
    ImageButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonReset, buttondelete;
    ArrayList<User> userDatabase;
    Button goToRegister;
    String[] oj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.loginButton);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);

        loginButton.setOnClickListener(loginButtonListener);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonReset = findViewById(R.id.button10);
        buttondelete = findViewById(R.id.button11);

        button0.setOnClickListener(numberButtonListener);
        button1.setOnClickListener(numberButtonListener);
        button2.setOnClickListener(numberButtonListener);
        button3.setOnClickListener(numberButtonListener);
        button4.setOnClickListener(numberButtonListener);
        button5.setOnClickListener(numberButtonListener);
        button6.setOnClickListener(numberButtonListener);
        button7.setOnClickListener(numberButtonListener);
        button8.setOnClickListener(numberButtonListener);
        button9.setOnClickListener(numberButtonListener);
        buttonReset.setOnClickListener(numberButtonListener);
        buttondelete.setOnClickListener(numberButtonListener);

        goToRegister = findViewById(R.id.login_goToRegister);
        goToRegister.setOnClickListener(goToRegisterListener);

        userDatabase = new ArrayList<User>();
/*
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            // put snapShot in Products.class ...... only children added
                            // in This case Children Will be price, quantity
                            User user = userSnapshot.getValue(User.class);

                            // set id manually to tha same instance of the class
                            user.setIndex(userSnapshot.getKey());

                            // add the Post class to ArrayList
                            userDatabase.add(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );*/
    }

    View.OnClickListener goToRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener numberButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int inx = phoneNumberTextView.getText().length();
            if (inx >= 13 && v.getId() != R.id.button10 && v.getId() != R.id.button11) {
                // Message.information("입력 수 초과","핸드폰 길이 초과입니다");
                Toast.makeText(Login.this, "더 이상 입력하실 수 없습니다..", Toast.LENGTH_LONG).show();
                return;
            }
            if (inx == 3 || inx == 8) {
                phoneNumberTextView.append("-");
            }
            //if(Math.round(phoneNumberTextView.getPaint())>11)
            switch (v.getId()) {
                case R.id.button0:
                    phoneNumberTextView.append("0");
                    break;
                case R.id.button1:
                    phoneNumberTextView.append("1");
                    break;
                case R.id.button2:
                    phoneNumberTextView.append("2");
                    break;
                case R.id.button3:
                    phoneNumberTextView.append("3");
                    break;
                case R.id.button4:
                    phoneNumberTextView.append("4");
                    break;
                case R.id.button5:
                    phoneNumberTextView.append("5");
                    break;
                case R.id.button6:
                    phoneNumberTextView.append("6");
                    break;
                case R.id.button7:
                    phoneNumberTextView.append("7");
                    break;
                case R.id.button8:
                    phoneNumberTextView.append("8");
                    break;
                case R.id.button9:
                    phoneNumberTextView.append("9");
                    break;
                case R.id.button10:
                    int len = phoneNumberTextView.getText().length();
                    if (len > 0) {
                        phoneNumberTextView.setText("");
                    }
                    break;
                //한글자씩 지우는 기능임.
                case R.id.button11:
                    if (inx == 0) {
                        return;
                    } else {
                        String str = phoneNumberTextView.getText().toString();
                        String renewal = str.substring(0, str.length() - 1);
                        phoneNumberTextView.setText(renewal);
                    }
                    break;
            }
        }
    };

    View.OnClickListener loginButtonListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View view) {
            //if(핸드폰 번호가 11자리 이하일 때){ 다시 입력하세요 다이얼로그}
            //if(핸드폰 번호가 존재하지 않는 번호일 때{ 가입되있지 않은 번호입니다 다이얼로그}
            String str = phoneNumberTextView.getText().toString();
            String resultNum = splitPhoneNumber(str);
            if (str.length() == 13) { //전화번호 총 13자리 일 떄.
                if (checkUser(resultNum)) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.putExtra("name", oj[1]);
                    intent.putExtra("phoneNumber", str);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(Login.this, "입력 양식이 잘못되었습니다.", Toast.LENGTH_LONG).show();
            }
        }
    };

    public boolean checkUser(String phoneNumber) {
        String result = "";
        try {
            result = new CustomTask().execute(phoneNumber, "", "", "", "login").get();
            oj = result.split("\t");
            Log.i("chanmi", "CustomTask().execute : " + result + "OK");
            Log.i("chanmi","oj[1]="+oj[1]);
            if (oj[0].equals("loginOK")) {
                return true;
            } else if (oj[0].equals("wrongPhoneNumber")) {
                Message.information(Login.this, "알림", "전화번호를 확인해주세요.");
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }


    public String splitPhoneNumber(String phoneNumber) {
        String[] array = phoneNumber.split("-");
        String result = "";
        for (int i = 0; i < array.length; i++) {
            result = result + array[i];
        }
        return result;
    }
}
