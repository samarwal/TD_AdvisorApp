package com.example.td_adviser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.td_adviser.davinci.Transaction;

import java.util.Map;

public class FinancialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial);

        // Rating bar calculations
        double netTransactions = 0.0;
        for (Transaction tx : MainActivity.CURRENT_CUSTOMER.getTransactions()) {
            netTransactions += tx.getAmount();
        }
        double quarterlyIncome = MainActivity.CURRENT_CUSTOMER.getTotalIncome() / 4.0;
        double spendingRatio = netTransactions / quarterlyIncome * 100.0;
        int ranking;
        if (spendingRatio >= 85) {
            ranking = 1;
        } else if (spendingRatio >= 70 && spendingRatio < 85) {
            ranking = 2;
        } else {
            ranking = 3;
        }
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(ranking);
        ratingBar.setVisibility(View.VISIBLE);

        // Messages
        TextView messageTextView = (TextView) findViewById(R.id.financialMessage);
        if (ranking == 1) {
            messageTextView.setText(MainActivity.CURRENT_CUSTOMER.getFirstName() + ", over the last 3 months you spent " + ((int) spendingRatio) + "% of your income. Consider spending less in your top spending categories shown below. It is also highly recommended that you talk to a nearby financial advisor.");
        } else if (ranking == 2) {
            messageTextView.setText(MainActivity.CURRENT_CUSTOMER.getFirstName() + ", over the last 3 months you spent " + ((int) spendingRatio) + "% of your income. Be aware of your top spending categories shown below.");
        } else {
            messageTextView.setText(MainActivity.CURRENT_CUSTOMER.getFirstName() + ", over the last 3 months you spent " + ((int) spendingRatio) + "% of your income. Your top spending categories are shown below.");
        }
        messageTextView.setVisibility(View.VISIBLE);
        TextView advisorTextView = (TextView) findViewById(R.id.financialAdvisor);
        advisorTextView.setText((ranking == 1 ? "Recommended " : "") + "Financial Advisor\nJohn Smith\n(416) 555-5555");
        advisorTextView.setVisibility(View.VISIBLE);

        // Top 3 spending categories
        TextView spendingTextView = (TextView) findViewById(R.id.financialSpending);
        Map<String, Double> transactionCategoryCounts = MainActivity.CURRENT_CUSTOMER.getTransactionCategoryCounts();
        String[] topCategories = {null, null, null};
        double[] topCategoryCounts = {-1, -1, -1};
        for (String category : transactionCategoryCounts.keySet()) {
            double count = transactionCategoryCounts.get(category);
            if (count > topCategoryCounts[0]) {
                topCategoryCounts[2] = topCategoryCounts[1];
                topCategories[2] = topCategories[1];
                topCategoryCounts[1] = topCategoryCounts[0];
                topCategories[1] = topCategories[0];
                topCategoryCounts[0] = count;
                topCategories[0] = category;
            } else if (count > topCategoryCounts[1]) {
                topCategoryCounts[2] = topCategoryCounts[1];
                topCategories[2] = topCategories[1];
                topCategoryCounts[1] = count;
                topCategories[1] = category;
            } else if (count > topCategoryCounts[2]) {
                topCategoryCounts[2] = count;
                topCategories[2] = category;
            }
        }
        spendingTextView.append("\n1) " + topCategories[0]);
        spendingTextView.append("\n2) " + topCategories[1]);
        spendingTextView.append("\n3) " + topCategories[2]);
        spendingTextView.setVisibility(View.VISIBLE);

        // Remove the loading bar
        ((ProgressBar) findViewById(R.id.progressBar3)).setVisibility(View.INVISIBLE);
    }

}
