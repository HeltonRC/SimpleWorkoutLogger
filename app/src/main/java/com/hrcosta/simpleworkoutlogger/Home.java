package com.hrcosta.simpleworkoutlogger;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

class Home extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            startActivity(new Intent(Home.this, CalendarActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
