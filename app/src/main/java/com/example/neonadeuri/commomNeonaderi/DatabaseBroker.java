package com.example.neonadeuri.commomNeonaderi;

import android.content.Context;
import android.media.audiofx.BassBoost;

import java.util.ArrayList;

public abstract class DatabaseBroker {
    public interface OnDataBrokerListener {
        public void onChange(String databaseStr);
    }

    // user -----------------------------------------------------------------------
    protected String userDatabaseStr = "";
    protected OnDataBrokerListener userOnDataBrokerListener = null;

    //-------------------------------------------------------------------------------
    // database -----------------------------------------------------------------------
    OnDataBrokerListener checkOnDataBrokerListener = null;

    //public abstract  void setGroupOnDataBrokerListener(Context context, OnDataBrokerListener onDataBrokerListener);
    //public abstract ArrayList<String> loadGroupDatabase(Context context);
    // public abstract void saveGroupDatabase(Context context, ArrayList<String> groupDatabase);

    public abstract void setUserOnDataBrokerListener(Context context, OnDataBrokerListener onDataBrokerListener);

    public abstract ArrayList<User> loadUserDatabase(Context context);

    public abstract void saveUserDatabase(Context context, ArrayList<User> userDatabase);

    //public abstract  void setBookingOnDataBrokerListener(Context context, String userGroup, OnDataBrokerListener onDataBrokerListener);
    //public abstract String[] loadBookingDatabase(Context context, String userGroup);
    //public abstract void saveBookingDatabase(Context context, String userGroup, String[] bookingDatabase);

    //public abstract  void setSettingsOnDataBrokerListener(Context context, OnDataBrokerListener onDataBrokerListener);
    //public abstract Settings loadSettingsDatabase(Context context);
    // public abstract void saveSettingsDatabase(Context context, BassBoost.Settings settingsDatabase);

    public abstract void setCheckDatabaseRoot(OnDataBrokerListener onDataBrokerListener);

    public abstract void resetDatabase(Context context);


    public String rootPath = "";

    public static DatabaseBroker createDatabaseObject(String rootPath) {
        DatabaseBroker databaseBroker;
        if (rootPath.equals("ccmDb")) {
            databaseBroker = new FirebaseBroker();
        } else {
            databaseBroker = new FirebaseBroker();    // it will be replaced as FirebaseBroker
        }
        databaseBroker.rootPath = rootPath;
        return databaseBroker;
    }
}
