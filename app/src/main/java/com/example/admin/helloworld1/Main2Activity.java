package com.example.admin.helloworld1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {
    private TextView textView;
    private Button button1;
    private String[] data={"北京","上海","天津"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(
                Main2Activity.this,android.R.layout.simple_list_item_1,data);
        ListView listView =(ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        this.textView =(TextView) findViewById(R.id.textView);
        this.button1 =(Button)findViewById(R.id.button1);
        this.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this,MainActivity.class));
            }
        });
        String weatherUrl = "http://guolin.tech/api/china";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            final String responseText = response.body().string();
            String[]  result=parseJSONObject(responseText);
                Main2Activity.this.data=result;
            runOnUiThread(new Runnable() {
                @Override
                public void run() { textView.setText(responseText);
                }
            });
            }
        });
    }
    private String[] parseJSONObject(String responseText){
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(responseText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] result=new String[jsonArray.length()];
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                result[i]=jsonObject.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
