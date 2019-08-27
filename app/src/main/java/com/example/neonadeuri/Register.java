package com.example.neonadeuri;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neonadeuri.commomNeonaderi.FirebasePost;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mPostReference;
    Button btn_register;
    EditText edit_PhoneNumber;
    EditText edit_Name;
    EditText edit_Age;
    CheckBox check_Man;
    CheckBox check_Woman;

    String PhoneNumber;
    String name;
    long age;
    String gender = "";
    String sort = "phoneNumber";

    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();

    Button btn_gotoLogin;
    Long key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        btn_register = findViewById(R.id.submit);
        btn_register.setOnClickListener(Register.this);

        edit_PhoneNumber = findViewById(R.id.register_PhoneNumber);
        edit_PhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int cnt = edit_PhoneNumber.getText().length();
                if (cnt == 3 || cnt == 8) {
                    edit_PhoneNumber.append("-");
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
        edit_Name = findViewById(R.id.register_name);
        edit_Age = findViewById(R.id.register_age);

        check_Man = findViewById(R.id.register_check_man);
        check_Man.setOnClickListener(this);
        check_Woman = findViewById(R.id.register_check_woman);
        check_Woman.setOnClickListener(this);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        ListView listView;
        getFirebaseDatabase();
        btn_register.setEnabled(true);

        btn_gotoLogin = findViewById(R.id.register_goToLogin_button);
        btn_gotoLogin.setOnClickListener(Register.this);
    }

    public void setRegissterMode() {
        edit_PhoneNumber.setText("");
        edit_Name.setText("");
        edit_Age.setText("");
        check_Man.setChecked(false);
        check_Woman.setChecked(false);
        btn_register.setEnabled(true);
    }

    public boolean IsExistPhoneNumber() {
        boolean IsExist = arrayIndex.contains(PhoneNumber);
        return IsExist;
    }

    public void postFirebaseDatabase(boolean add) {
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if (add) {
            FirebasePost post = new FirebasePost(PhoneNumber, name, age, gender);
            postValues = post.toMap();
        } else {
            Toast.makeText(getApplicationContext(), "이미 존재하는 계정입니다.", Toast.LENGTH_LONG).show();
        }
        childUpdates.put("/id_list/" + "Member " + key, postValues);
        mPostReference.updateChildren(childUpdates);
        //mPostReference.child("/id_list/").setValue(postValues);
    }

    public void getFirebaseDatabase() {
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                key = dataSnapshot.getChildrenCount();
                arrayData.clear();
                arrayIndex.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("getFirebaseDatabase", "loadPost:onCancelled", databaseError.toException());
            }
        };
        Query sortbyAge = FirebaseDatabase.getInstance().getReference().child("id_list").orderByChild(sort);
        sortbyAge.addListenerForSingleValueEvent(postListener);
    }

    public String setTextLength(String text, int length) {
        if (text.length() < length) {
            int gap = length - text.length();
            for (int i = 0; i < gap; i++) {
                text = text + " ";
            }
        }
        return text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                String pn = edit_PhoneNumber.getText().toString();
                PhoneNumber = splitPhoneNumber(pn);
                name = edit_Name.getText().toString();
                age = Long.parseLong(edit_Age.getText().toString());
                if (!IsExistPhoneNumber()) {
                    postFirebaseDatabase(true);
                    getFirebaseDatabase();
                    setRegissterMode();
                    Toast.makeText(Register.this, "회원 가입 성공" + PhoneNumber, Toast.LENGTH_LONG).show();
                } else if (PhoneNumber == "" || name == "") {
                    Toast.makeText(getApplicationContext(), "다시 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Register.this, "이미 존재하는 ID 입니다. 다른 ID로 설정해주세요.", Toast.LENGTH_LONG).show();
                }
                edit_PhoneNumber.requestFocus();
                edit_PhoneNumber.setCursorVisible(true);
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
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
        }
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
