package com.example.admin.helloworld1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProvinceActivity extends AppCompatActivity {

    private  ListView listView;
    private List<String> data2=new ArrayList(); private List<Integer>pids = new ArrayList<>();
    private List<String>  data=new ArrayList<>();
    private  String currentlevel="province";
    private  int pid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.listView =(ListView) findViewById(R.id.list_view);
        final ArrayAdapter<String> adapter =new ArrayAdapter<String>(
                ProvinceActivity.this,android.R.layout.simple_list_item_1,data);
      listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击了哪一个",""+position+":"+ProvinceActivity.this.pids.get(position)+":"+ProvinceActivity.this.data.get(position));
               // Intent intent = new Intent(ProvinceActivity.this,CityActivity.class);
                //intent.putExtra("pid",ProvinceActivity.this.pids[position]);
                pid=ProvinceActivity.this.pids.get(position);
                currentlevel="city";
                one(adapter);
               // if(currentlevel == "city"){
                  //  intent.putExtra("cid",cids[position]);
              //  }
                //startActivity(intent);
            }
        });
        one(adapter);
    }



    private void one(final ArrayAdapter<String> adapter)  {
        String weatherUrl=currentlevel=="city"?"http://guolin.tech/api/china/"+pid:"http://guolin.tech/api/china";
        //String weatherUrl = "http://guolin.tech/api/china";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
             final String responseText = response.body().string();
                parseJSONObject(responseText);

            runOnUiThread(new Runnable() {
                @Override
                public void run() { //textView.setText(responseText);
                    adapter.notifyDataSetChanged();
                }
            });
            }
        });
    }

    private void parseJSONObject(String responseText) {
        JSONArray jsonArray = null;
        this.data.clear();
        this.pids.clear();
        try {
            jsonArray = new JSONArray(responseText);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                this.data.add(jsonObject.getString("name"));
                this.pids.add(jsonObject.getInt("id"));



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


