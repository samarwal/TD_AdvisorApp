package com.example.td_adviser;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.td_adviser.davinci.Customer;

public class MainActivity extends AppCompatActivity {

    public static Customer CURRENT_CUSTOMER = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadUserData(new Runnable() {
            @Override
            public void run() {
                dismissSplash();
            }
        });
    }

    private static void loadUserData(final Runnable onFinish) {


        onFinish.run();
    }

    private void dismissSplash() {
        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
