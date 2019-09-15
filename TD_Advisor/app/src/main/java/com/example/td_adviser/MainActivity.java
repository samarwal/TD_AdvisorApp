package com.example.td_adviser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.td_adviser.davinci.BankAccount;
import com.example.td_adviser.davinci.Customer;
import com.example.td_adviser.davinci.Transaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJDQlAiLCJ0ZWFtX2lkIjoiZWVhN2UxMTYtMTkxYy0zY2JhLTkxNzAtMzNhMzhjNGMwN2ZlIiwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1LCJhcHBfaWQiOiI3M2YzM2M2NS1lMzBkLTQ4OGQtODY0YS02N2Q0MTg1MDVjN2MifQ.Sz2raIJhbMb9MEjaDWq0Ift_B-9ibb5UpiAzXqi-m2c";
    private static final String GET_1000_USERS_LINK = "https://api.td-davinci.com/api/raw-customer-data";
    private static final String GET_ACCOUNTS_LINK = "https://api.td-davinci.com/api/customers/%s/accounts";
    private static final String GET_TRANSACTIONS_LINK = "https://api.td-davinci.com/api/customers/%s/transactions";

    public static Customer CURRENT_CUSTOMER = null;

    private static RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        loadUserData(this, findViewById(R.id.myMainActivity), new Runnable() {
            @Override
            public void run() { dismissSplash();
            }
        });
    }

    private static void loadUserData(final Context ctx, final View view, final Runnable onFinish) {
        StringRequest get1000UsersRequest = new StringRequest(Request.Method.POST, GET_1000_USERS_LINK,
                // Response handler
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONObject result = json.getJSONObject("result");
                            JSONArray customers = result.getJSONArray("customers");

                            int randomIndex = new Random().nextInt(500);
                            for (int customerIndex = 0; customerIndex < customers.length(); customerIndex++) {
                                JSONObject customer = customers.getJSONObject(customerIndex);
                                try {
                                    if (customer.getString("type").equalsIgnoreCase("personal")) {
                                        randomIndex--;
                                        if (randomIndex == 0) {
                                            String id = customer.getString("id");
                                            String firstName = customer.getString("givenName"),
                                                    lastName = customer.getString("surname");
                                            int age = customer.getInt("age");
                                            boolean isMale = customer.getString("gender").equalsIgnoreCase("male");
                                            String birthDate = customer.getString("birthDate");
                                            double totalIncome = customer.getDouble("totalIncome");

                                            CURRENT_CUSTOMER = new Customer(id, firstName, lastName, age, isMale, birthDate, totalIncome);
                                            requestQueue.add(loadAccountsData(ctx, view, CURRENT_CUSTOMER.getId(), onFinish));
                                            break;
                                        }
                                    }
                                } catch (Exception e) {
                                    // Other type of customer (not personal)
                                }
                            }
                        } catch (Exception e) {
                            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
                        }
                    }
                },
                // Error handler
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(view, R.string.failed_to_load_msg, Snackbar.LENGTH_INDEFINITE).show();
                    }
                }
        ) {
            // Header handler
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", API_KEY);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(get1000UsersRequest);
    }

    private static StringRequest loadAccountsData(final Context ctx, final View view, final String customerId, final Runnable onFinish) {
        StringRequest getAccountsRequest = new StringRequest(Request.Method.GET, String.format(GET_ACCOUNTS_LINK, customerId),
                // Response handler
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONObject result = json.getJSONObject("result");
                            JSONArray bankAccounts = result.getJSONArray("bankAccounts");

                            for (int bankIndex = 0; bankIndex < bankAccounts.length(); bankIndex++) {
                                JSONObject bankAccount = bankAccounts.getJSONObject(bankIndex);
                                String id = bankAccount.getString("id");
                                String type = bankAccount.getString("type");
                                double balance = bankAccount.getDouble("balance");
                                String branchNumber = bankAccount.getString("branchNumber");
                                BankAccount account = new BankAccount(id, type.equalsIgnoreCase("sda"), balance, branchNumber);
                                CURRENT_CUSTOMER.addBankAccount(account);
                            }
                            requestQueue.add(loadTransactionsData(ctx, view, customerId, onFinish));
                        } catch (Exception e) {
                            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(view, R.string.failed_to_load_acct_msg, Snackbar.LENGTH_INDEFINITE).show();
                    }
                }
        ) {
            // Header handler
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", API_KEY);
                headers.put("Content-Type", "application/json");
                return headers;
            }
            // Params handler
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("custId", customerId);
                return params;
            }
        };
        return getAccountsRequest;
    }

    private static StringRequest loadTransactionsData(final Context ctx, final View view, final String customerId, final Runnable onFinish) {
        StringRequest getTransactionsRequest = new StringRequest(Request.Method.GET, String.format(GET_TRANSACTIONS_LINK, customerId),
                // Response handler
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONArray result = json.getJSONArray("result");

                            for (int txIndex = 0; txIndex < result.length(); txIndex++) {
                                JSONObject transaction = result.getJSONObject(txIndex);
                                String id = transaction.getString("id");
                                String customerId = transaction.getString("customerId");
                                String merchantId = transaction.has("merchantId") ? transaction.getString("merchantId") : "";
                                String description = transaction.getString("description");
                                double amount = transaction.getDouble("currencyAmount");
                                Vector<String> categories = new Vector<String>();
                                JSONArray categoriesJson = transaction.getJSONArray("categoryTags");
                                for (int categoryIndex = 0; categoryIndex < categoriesJson.length(); categoryIndex++) {
                                    String category = categoriesJson.getString(categoryIndex);
                                    categories.add(category);
                                }
                                Transaction tx = new Transaction(id, customerId, merchantId, description, amount, categories);
                                CURRENT_CUSTOMER.addTransaction(tx);
                            }
                            onFinish.run();
                        } catch (Exception e) {
                            Snackbar.make(view, R.string.failed_to_load_tx_msg, Snackbar.LENGTH_INDEFINITE).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(view, R.string.failed_to_load_tx_msg, Snackbar.LENGTH_INDEFINITE).show();
                    }
                }
        ) {
            // Header handler
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", API_KEY);
                headers.put("Content-Type", "application/json");
                return headers;
            }
            // Params handler
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("custId", customerId);
                return params;
            }
        };
        return getTransactionsRequest;
    }

    private void dismissSplash() {
        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
