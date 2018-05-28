package org.judocanada.judocanadamobileappandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                finish();
            }
        }, 3000);

    }

    @Override
    public void finish(){
        User user = UserManager.getInstance().getUser(this);

        Intent result;
        if(user != null)
            result= new Intent(SplashScreen.this, MainActivity.class);
        else
            result= new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(result);
        super.finish();
    }

}
