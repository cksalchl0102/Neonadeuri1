package com.example.neonadeuri;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        btn_register = findViewById(R.id.submit);
        btn_register.setOnClickListener(Register.this);

        edit_PhoneNumber = findViewById(R.id.register_PhoneNumber);
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
        }

        childUpdates.put("/id_list/" + PhoneNumber, postValues);
        mPostReference.updateChildren(childUpdates);
    }

    public void getFirebaseDatabase() {
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                arrayData.clear();
                arrayIndex.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                  /*  String key = postSnapshot.getKey();
                    FirebasePost get = postSnapshot.getValue(FirebasePost.class);
                    String[] info = {get.phoneNumber, get.name, String.valueOf(get.age), get.gender};
                    String Result = setTextLength(info[0], 20) + setTextLength(info[1], 20) + setTextLength(info[2], 10) + setTextLength(info[3], 10);
                    arrayData.add(Result);

                    arrayIndex.add(key);
                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + info[0] + info[1] + info[2] + info[3]);
*/
                }/*
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String value1 = ds.child("phoneNumber").getValue(String.class);
                    String value2 = ds.child("name").getValue(String.class);
                    String value3 = ds.child("age").getValue(String.class);
                    String value4 = ds.child("gender").getValue(String.class);

                    FirebasePost get = ds.getValue(FirebasePost.class);
                    String Result = setTextLength(value1, 20) + setTextLength(value2, 20) + setTextLength(value3, 10) + setTextLength(value4, 10);
                    arrayData.add(Result);

                }*/
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
                PhoneNumber = edit_PhoneNumber.getText().toString();
                name = edit_Name.getText().toString();
                age = Long.parseLong(edit_Age.getText().toString());
                if (!IsExistPhoneNumber()) {
                    postFirebaseDatabase(true);
                    getFirebaseDatabase();
                    setRegissterMode();
                    Toast.makeText(Register.this, "회원 가입 성공", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
        }
    }
}
