package com.example.neonadeuri.commomNeonaderi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
public class FirebaseBroker extends DatabaseBroker{
    @Override
    public void setUserOnDataBrokerListener(Context context, OnDataBrokerListener onDataBrokerListener) {

    }

    @Override
    public ArrayList<User> loadUserDatabase(Context context) {
        return null;
    }

    @Override
    public void saveUserDatabase(Context context, ArrayList<User> userDatabase) {

    }

    @Override
    public void setCheckDatabaseRoot(OnDataBrokerListener onDataBrokerListener) {

    }

    @Override
    public void resetDatabase(Context context) {

    }
}
