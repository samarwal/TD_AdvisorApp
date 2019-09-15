package com.example.td_adviser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.td_adviser.davinci.BankAccount;

import java.util.Vector;

public class InvestmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);

        // Add links to TD investment options
        TextView mutualFunds = (TextView) findViewById(R.id.tdMutualFunds),
            portfolios = (TextView) findViewById(R.id.tdPortfolios),
            etfs = (TextView) findViewById(R.id.tdETFs),
            stocks = (TextView) findViewById(R.id.tdStocks);
        mutualFunds.setClickable(true);
        mutualFunds.setMovementMethod(LinkMovementMethod.getInstance());
        String mutualFundsText = "<a href='https://www.td.com/ca/en/personal-banking/products/saving-investing/mutual-funds/td-mutual-funds/'>Mutual funds</a>";
        mutualFunds.setText(Html.fromHtml(mutualFundsText));
        portfolios.setClickable(true);
        portfolios.setMovementMethod(LinkMovementMethod.getInstance());
        String portfoliosText = "<a href='https://www.td.com/ca/en/personal-banking/products/saving-investing/mutual-funds/td-comfort-portfolios/'>Portfolios</a>";
        portfolios.setText(Html.fromHtml(portfoliosText));
        etfs.setClickable(true);
        etfs.setMovementMethod(LinkMovementMethod.getInstance());
        String etfsText = "<a href='https://www.td.com/ca/en/asset-management/funds/solutions/etfs/'>ETFs</a>";
        etfs.setText(Html.fromHtml(etfsText));
        stocks.setClickable(true);
        stocks.setMovementMethod(LinkMovementMethod.getInstance());
        String stocksText = "<a href='https://www.td.com/ca/en/investing/direct-investing/'>Stocks</a>";
        stocks.setText(Html.fromHtml(stocksText));

        // Recommended annual investment
        TextView recommendedAnnualInvestmentText = (TextView) findViewById(R.id.annualInvestmentRecommendation);
        double recommendedInvestment = 0.15 * MainActivity.CURRENT_CUSTOMER.getTotalIncome();
        recommendedAnnualInvestmentText.append(" $" + ((int) recommendedInvestment));

        // Savings holdings
        TextView savingsHoldingsText = (TextView) findViewById(R.id.savingsHoldings);
        double savingsHoldings = 0.0;
        Vector<BankAccount> bankAccounts = MainActivity.CURRENT_CUSTOMER.getBankAccounts();
        for (BankAccount account : bankAccounts) {
            if (account.isSavings()) {
                savingsHoldings += account.getBalance();
            }
        }
        savingsHoldingsText.append(" $" + ((int) savingsHoldings));;

        // TFSA Opportunities
        TextView tfsaOpportunityText = (TextView) findViewById(R.id.tfsaOpportunity);
        double tfsaOpportunity = savingsHoldings;
        double yearsSinceEligible = MainActivity.CURRENT_CUSTOMER.getAge() - 10;
        int tempTfsaOpportunity = 0;
        if (yearsSinceEligible > 0) {
            yearsSinceEligible = Math.min(yearsSinceEligible, 10);
            int[] yearlyLimits = {6000, 5500, 5500, 5500, 10000, 5500, 5500, 5000, 5000, 5000, 5000};
            for (int yearIndex = 0; yearIndex < yearsSinceEligible; yearIndex++) {
                tempTfsaOpportunity += yearlyLimits[yearIndex];
            }
            tfsaOpportunity = Math.min(tfsaOpportunity, tempTfsaOpportunity);
        } else {
            tfsaOpportunity = 0.0;
        }
        tfsaOpportunityText.append(" $" + ((int) tfsaOpportunity) + " / $" + ((int) tempTfsaOpportunity));
    }
}
