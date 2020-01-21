package com.example.footapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class LiveView extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_view);
        textView = findViewById(R.id.textView2);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api-football-v1.p.rapidapi.com/v2/teams/team/33")
                .get()
                .addHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "c1603f93d7msh06bb2b6bfff93b9p1a279bjsnccfa2559b171")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse = response.body().string();
                    try {
                        JSONArray json = new JSONArray(response.body());
                        LiveView.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    textView.setText(json.getJSONObject(1).toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Na żywo");

        //        try {
//            HttpResponse<JsonNode> response = Unirest.get("https://api-football-v1.p.rapidapi.com/v2/teams/team/33")
//                    .header("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
//                    .header("x-rapidapi-key", "c1603f93d7msh06bb2b6bfff93b9p1a279bjsnccfa2559b171")
//                    .asJson();
//
//            //System.out.println(response.getBody());
//            String json = response.getBody().toString();
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }
    }
}
