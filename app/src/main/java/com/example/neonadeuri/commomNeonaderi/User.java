package com.example.neonadeuri.commomNeonaderi;

public class User {
    public String userPhoneNumber="000-0000-0000";
    public String firstNum, secondNum, thirdNum;

    //이건 DB 에서 불러와서 중복 확인해야함.
    public String userName, userPassword;

    public User(String str) { //전화번호만 받았을 떄 == 로그인
        init(str);
    }

    public User(String userName, String userPassword, String userPhoneNumber) {
        //회원가입 즉 회원 정보 + 전화번호를 받았을 떄.
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhoneNumber = userPhoneNumber;
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
