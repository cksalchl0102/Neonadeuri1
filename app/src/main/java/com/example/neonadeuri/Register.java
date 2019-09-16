package com.example.neonadeuri;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neonadeuri.commomNeonaderi.CustomTask;
import com.example.neonadeuri.commomNeonaderi.Message;

import java.util.concurrent.ExecutionException;

public class Register extends AppCompatActivity {
    EditText phoneEdit, nameEdit, ageEdit;
    CheckBox check_Man, check_Woman;
    Button registerButton, goLoginActivityButton;
    String phoneNumber, name, age, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        phoneEdit = findViewById(R.id.register_PhoneNumber);
        nameEdit = findViewById(R.id.register_name);
        ageEdit = findViewById(R.id.register_age);
        check_Man = findViewById(R.id.register_check_man);
        check_Woman = findViewById(R.id.register_check_woman);
        registerButton = findViewById(R.id.register_submit);
        goLoginActivityButton = findViewById(R.id.register_goToLogin_button);

        check_Man.setOnClickListener(Listener);
        check_Woman.setOnClickListener(Listener);
        registerButton.setOnClickListener(Listener);
        goLoginActivityButton.setOnClickListener(Listener);

        //phone EditText 의 편집 리스너
        phoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int cnt = phoneEdit.getText().length();
                if (cnt == 3 || cnt == 8) {
                    phoneEdit.append("-");
                }
                if (cnt >= 13) {
                    // Message.information("입력 수 초과","핸드폰 길이 초과입니다");
                    Toast.makeText(Register.this, "더 이상 입력하실 수 없습니다..", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    View.OnClickListener Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.register_submit:
                    phoneNumber = splitPhoneNumber(phoneEdit.getText().toString());
                    Log.i("chanmi", "phoneNumber.length() = " + phoneNumber.length());
                    name = nameEdit.getText().toString();
                    age = ageEdit.getText().toString();
                    if (phoneNumber.length() == 11 && name != null && age != null && gender != null) { //전화번호가 13자리이고
                        //모든 값들을 잘 적어 넣었다면.
                        if (registerUser(phoneNumber, name, age, gender)) {//회원 가입이 가능한 정보라면.
                            Message.information(Register.this, "알림", "회원가입 성공");
                        } else {
                            Message.information(Register.this, "알림", "이미 존재하는 전화번호입니다.");
                        }
                    } else {//값들을 유요하지 않게 적었다면.
                        Message.information(Register.this, "알림", "회원 정보를 정확하게 입력하세요.");
                    }
                    break;
                case R.id.register_check_man:
                    check_Woman.setChecked(false);
                    gender = "Man";
                    break;
                case R.id.register_check_woman:
                    check_Man.setChecked(false);
                    gender = "Woman";
                    break;
                case R.id.register_goToLogin_button:
                    Intent intent = new Intent(Register.this, Login.class);
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
                Message.information(Register.this, "알림", "이미 존재하는 번호입니다.");
                Log.i("chanmi", "이미 존재하는 전화번호입니다.");
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

    public String splitPhoneNumber(String phoneNumber) {
        String[] array = phoneNumber.split("-");
        String result = "";
        for (int i = 0; i < array.length; i++) {
            result = result + array[i];
        }
        return result;
    }
}
