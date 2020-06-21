package com.hfad.comment_section;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements comment_adapter.comment_listener{

    Button show_comments;
    EditText comments;
    RecyclerView recyclerView;
    comment_adapter comment_adapter;
    List<String> comment_list = new ArrayList<>();
    List<String> name_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show_comments = findViewById(R.id.show_comments);
        recyclerView = findViewById(R.id.recyclerView2);
        comments = findViewById(R.id.comment);


        comment_adapter = new comment_adapter(this, (com.hfad.comment_section.comment_adapter.comment_listener) this,comment_list,name_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(comment_adapter);
        load_comments();


        show_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_comments();
                comments.setText("");
            }
        });


    }


    public void load_comments()
    {
        comment_list.clear();
        name_list.clear();
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtb2JpbGVObyI6IjU1NTU1NTU1NTUiLCJ1c2VySWQiOiI1ZWU4ZTE5OGVlYzlkNzBjODY0NWI5OWEiLCJpYXQiOjE1OTI1NTI0MDUsImV4cCI6MTU5MjU3NDAwNX0.qNzjyoWhay_wcDCy-d6xjJego2EcS6fLmdBT1S6Sqpw";
        String postid="5eeca28bd058a30017fe1ae5";
        String url = "https://community-ebh.herokuapp.com/posts/"+postid+"/comment";
        //Toast.makeText(this,"yo",Toast.LENGTH_LONG).show();

        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("authorization",token)
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("xyz",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //Log.e("xyz",response.toString());
                //Log.e("xyz",response.body().string());
                String data = response.body().string();
                Log.e("xyz",data);
                try {
                    JSONArray myResponse = new JSONArray(data);

                    for(int i=myResponse.length()-1;i>=0;i--)
                    {
                        JSONObject object = myResponse.getJSONObject(i);
                        Log.e("xyz",object.getString("content"));
                        comment_list.add(object.getString("content"));
                        name_list.add("vatsu");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notify_change();
                    }
                });


            }
        });





    }

    public void add_comments()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("content", comments.getText().toString());

        } catch (JSONException e) {
            Toast.makeText(this,"1st",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtb2JpbGVObyI6IjU1NTU1NTU1NTUiLCJ1c2VySWQiOiI1ZWU4ZTE5OGVlYzlkNzBjODY0NWI5OWEiLCJpYXQiOjE1OTI1NTI0MDUsImV4cCI6MTU5MjU3NDAwNX0.qNzjyoWhay_wcDCy-d6xjJego2EcS6fLmdBT1S6Sqpw";
        String postid="5eeca28bd058a30017fe1ae5";
        String url = "https://community-ebh.herokuapp.com/posts/"+postid+"/comment";
        //Toast.makeText(this,"yo",Toast.LENGTH_LONG).show();

        final OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("authorization",token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_comments();
                    }
                });
            }
        });


    }


    public void notify_change()
    {
        comment_adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(name_list.size()-1);
    }




    @Override
    public void comment_click(int position) {
        Toast.makeText(this,position+"",Toast.LENGTH_LONG).show();
    }
}
