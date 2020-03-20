package com.example.barmaster.bbdd;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;


public class DataBaseACC extends Application {
    private final String APP_ID = "myAppId";
    private final String CLIENT_iD = "empty";
    private final String URL_SERVER = "https://swapp12.herokuapp.com/parse/";


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(APP_ID)
                // if defined
                .clientKey(CLIENT_iD)
                .server(URL_SERVER)
                .build()
        );

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
