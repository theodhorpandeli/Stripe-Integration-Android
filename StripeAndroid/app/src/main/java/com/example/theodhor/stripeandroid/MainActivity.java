package com.example.theodhor.stripeandroid;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.stripe.android.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Plan;
import com.stripe.model.PlanCollection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {


    Stripe stripe;
    ArrayList<SimplePlan> planArrayList;
    PlanCollection planCollection;
    RecyclerView recyclerView;
    ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stripe = new Stripe();
        try {
            stripe.setDefaultPublishableKey("[YOUR_PUBLISHABLE_KEY_TEST_HERE]");
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        planArrayList = new ArrayList<>();



        new Async().execute();
    }

    public void showRcv(ArrayList<SimplePlan> plans){
        adapter = new ItemsAdapter(this,plans);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public class Async extends AsyncTask<Void,String,ArrayList<SimplePlan>> {

        @Override
        protected ArrayList<SimplePlan> doInBackground(Void... params) {

            try {
                String line, newjson = "";
                URL url = new URL("[YOUR_SERVER_CHARGE_SCRIPT_URL]");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                    while ((line = reader.readLine()) != null) {
                        newjson += line;
                        // System.out.println(line);
                    }
                    // System.out.println(newjson);
                    String json = newjson.toString();
                    JSONObject jObj = new JSONObject(json);
                    Log.e("Obj",jObj.toString());
                    JSONArray plans = jObj.getJSONArray("plans");
                    for (int i=0;i<plans.length();i++){
                        JSONObject plan = plans.getJSONObject(i);
                        plan.getString("amount");
                        Log.e("Amount",plan.getString("amount"));
                        planArrayList.add(new SimplePlan(plan.getInt("amount"),plan.getString("name"),plan.getString("statement_descriptor")));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return planArrayList;
        }

        @Override
        protected void onPostExecute(final ArrayList<SimplePlan> plan) {
            super.onPostExecute(plan);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showRcv(plan);
                }
            },3000);
        }
    }

    public class SimplePlan{
        String  name, description;
        Integer amount;

        public SimplePlan(Integer amount, String name, String description) {
            this.amount = amount;
            this.name = name;
            this.description = description;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
