package com.example.neonadeuri.commomNeonaderi;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {
    public String phoneNumber;
    public String name;
    public Long age;
    public String gender;
    public String password;
    public FirebasePost(){

    }
    public FirebasePost(String phoneNumber,String name,Long age,String gender){
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("phoneNumber",phoneNumber);
        result.put("name",name);
        result.put("age",age);
        result.put("gender",gender);
        return result;
    }
}
