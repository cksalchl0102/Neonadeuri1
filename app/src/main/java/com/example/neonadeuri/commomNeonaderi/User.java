package com.example.neonadeuri.commomNeonaderi;

import java.util.ArrayList;

public class User extends ArrayList<User> {
    public String userPhoneNumber;
    public String userGender;
    public String firstNum, secondNum, thirdNum;
    public String index;

    //이건 DB 에서 불러와서 중복 확인해야함.
    public String userName, userPassword;
    public String userAge;
    public User(){}

    public User(String str) { //전화번호만 받았을 떄 == 로그인
        init(str);
    }

    public User(String userPhoneNumber, String userName, String userAge,String userGender) {
        //회원가입 즉 회원 정보 + 전화번호를 받았을 떄.
       this.userPhoneNumber = userPhoneNumber;
       this.userName = userName;
       this.userAge = userAge;
       this.userGender = userGender;
    }
    public String getIndex(){
        return index;
    }
    public String getUserPhoneNumber(){
        return userPhoneNumber;
    }
    public String getUserName(){
        return userName;
    }
    public String getUserGender(){
        return userGender;
    }
    public void setIndex(String index){
        this.index = index;
    }

    public void init(String str) {
        String[] phoneNum = str.split("-");
        firstNum = "";
        secondNum = "";
        thirdNum = "";

        if (phoneNum.length == 0) return;
        if (phoneNum.length == 1) {
            firstNum = (phoneNum[0] != null) ? phoneNum[0] : "";
            return;
        }
        if (phoneNum.length == 2) {
            firstNum = (phoneNum[0] != null) ? phoneNum[0] : "";
            secondNum = (phoneNum[1] != null) ? phoneNum[1] : "";
            return;
        }
        firstNum = "010"; //or (phoneNum[0] != null)? phoneNum[0]:"";
        secondNum = (phoneNum[1] != null) ? phoneNum[1] : "";
        thirdNum = (phoneNum[2] != null) ? phoneNum[2] : "";
        userPhoneNumber = firstNum + secondNum + thirdNum;
    }

    public String toString() {
        return "sss";
    }

    public boolean isMeByName(String name) {
        return name.equals(userName);
    }

    public boolean isMeByPassword(String password) {
        return password.equals(userPassword);
    }

    public boolean isMeByPhoneNumber(String phoneNumber) {
        return userPhoneNumber.equals(phoneNumber);
    }
}
