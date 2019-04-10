package com.example.admin.helloworld1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textView = (TextView) findViewById(R.id.abc);
        this.button =(Button)findViewById(R.id.button);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });



        String weatherId = "CN101210704";
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=258bb6d5f98f4264aac4e581aa8b01f5";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       textView.setText(responseText);
                   }
               });

            }


        });
    }
}
