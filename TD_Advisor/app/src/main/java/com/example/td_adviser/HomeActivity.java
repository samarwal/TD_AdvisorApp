package com.example.td_adviser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFinancialActivity();
            }
        });

        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMortgage();
            }
        });

        button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInvestmentActivity();
            }
        });

    }

    public void openFinancialActivity() {
        Intent intent = new Intent(this, FinancialActivity.class);
        startActivity(intent);
    }

    public void openMortgage() {
        Intent intent = new Intent(this, Mortgage.class);
        startActivity(intent);
    }

    public void openInvestmentActivity() {
        Intent intent = new Intent(this, InvestmentActivity.class);
        startActivity(intent);
    }
}
