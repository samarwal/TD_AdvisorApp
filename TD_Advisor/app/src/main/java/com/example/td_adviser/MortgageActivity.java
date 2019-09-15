package com.example.td_adviser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.td_adviser.davinci.BankAccount;

import java.util.Vector;

public class MortgageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage);

        TextView advisorText = (TextView) findViewById(R.id.financialAdvisor4);
        advisorText.setText("Financial advisor\nJohn Smith\n(416) 555-5555");

        // Savings holdings
        TextView savingsHoldingsText = (TextView) findViewById(R.id.loanSavingsHoldings);
        double savingsHoldings = 0.0;
        Vector<BankAccount> bankAccounts = MainActivity.CURRENT_CUSTOMER.getBankAccounts();
        for (BankAccount account : bankAccounts) {
            if (account.isSavings()) {
                savingsHoldings += account.getBalance();
            }
        }
        savingsHoldingsText.append(" $" + ((int) savingsHoldings));

        // Down payment
//        TextView downPaymentText = (TextView) findViewById(R.id.loanMoneyDown);
//        double downPayment = 0.9 * savingsHoldings;
//        downPaymentText.append(" $" + ((int) downPayment));
        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    // Loan amounts
                    double downPayment = Double.parseDouble(editText.getText().toString());
                    double pmt = MainActivity.CURRENT_CUSTOMER.getTotalIncome() / 12 * 0.4;
                    double r = 0.06;
                    double n = 300;
                    // Monthly
                    double pv_monthly = (downPayment * 4) * ((r/12) * Math.pow(1 + (r/12), 300)) / (Math.pow(1 + (r/12), 300) - 1);
                    // Bi-weekly
                    double pv_biweekly = (downPayment * 4) * ((r/26) * Math.pow(1 + (r/26), 650)) / (Math.pow(1 + (r/26), 650) - 1);
                    // Weekly
                    double pv_weekly = (downPayment * 4) * ((r/52) * Math.pow(1 + (r/52), 1300)) / (Math.pow(1 + (r/52), 1300) - 1);

                    TextView weeklyText = (TextView) findViewById(R.id.loanPaymentWeekly),
                            biweeklyText = (TextView) findViewById(R.id.loanPaymentBiweekly),
                            monthlyText = (TextView) findViewById(R.id.loanPaymentMonthly);
                    weeklyText.setText("Weekly: $" + ((int) pv_weekly));
                    biweeklyText.setText("Bi-weekly: $" + ((int) pv_biweekly));
                    monthlyText.setText("Monthly: $" + ((int) pv_monthly));
                }
                return false;
            }
        });
    }
}
