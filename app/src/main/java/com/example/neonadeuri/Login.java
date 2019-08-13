package com.example.neonadeuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neonadeuri.commomNeonaderi.*;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    //일단 이거로 설정
    String phoneNum = "";
    String name = "최찬미";
    String pw = "123456";
    //이벤트 사용자의 번호 입력.
    TextView phoneNumberTextView;
    ImageButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonReset, buttondelete;
    String rootPath = "ccmDb";
    ArrayList<User> userDatabase;
    DatabaseBroker databaseBroker;

    Button goToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseBroker = DatabaseBroker.createDatabaseObject(rootPath);
        databaseBroker.setUserOnDataBrokerListener(this, onUserListener);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.loginButton);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        //phoneNum = phoneNumber.getText().toString();


        //String[] formatPhoneNumber = phoneNumber.split("-")
        //String sendPhoneNumData="";
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
    }
    View.OnClickListener goToRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(),Register.class);
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
                case R.id.button11:
                    int length = phoneNumberTextView.getText().length();
                    String str = phoneNumberTextView.getText().toString();
                    str = str.substring(0, length - 1);
                    if (length > 0) {
                        phoneNumberTextView.setText(str);
                    }
                    break;

            }
        }
    };

    View.OnClickListener loginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //if(핸드폰 번호가 11자리 이하일 때){ 다시 입력하세요 다이얼로그}
            //if(핸드폰 번호가 존재하지 않는 번호일 때{ 가입되있지 않은 번호입니다 다이얼로그}
            String str = phoneNumberTextView.getText().toString();
            if (str.length() == 13) {
                for (int i = 0; i < userDatabase.size(); i++) {
                    User user = userDatabase.get(i);
                    if (user.isMeByPhoneNumber(str)) {
                        phoneNum = phoneNumberTextView.getText().toString();
                        //User user = new User(phoneNum);
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        intent.putExtra("phoneNumber", phoneNum);
                        Toast.makeText(Login.this, "로그인을 환영합니다.", Toast.LENGTH_LONG).show();
                        //Message.information(Login.this,"로그인 성공","로그인하셨습니다. ");
                        startActivity(intent);
                        finish();
                    }
                }
            } else {
                Toast.makeText(Login.this, "입력 양식이 잘못되었습니다.", Toast.LENGTH_LONG).show();
            }

        }
    };
    DatabaseBroker.OnDataBrokerListener onUserListener = new DatabaseBroker.OnDataBrokerListener() {
        @Override
        public void onChange(String databaseStr) {
            userDatabase = databaseBroker.loadUserDatabase(Login.this);//userData 저장한 곳!!!!!!!
        }
    };
}
