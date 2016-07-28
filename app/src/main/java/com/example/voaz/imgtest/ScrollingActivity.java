package com.example.voaz.imgtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pojoobj.GoogleData;
import pojoobj.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ScrollingActivity extends Activity {
    private SearchView search;
    private RecyclerView mrecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        search = (SearchView) findViewById(R.id.searchView);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {

                String key = "AIzaSyAKxETwEuML8_k8kTYQlULkqN8M8I8xVqk";
                String cx  = "016873079342806567471:0fgymbpophy";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(GoogleAPICSE.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GoogleAPICSE googleapicse = retrofit.create(GoogleAPICSE.class);
                String sch = search.getQuery().toString();
                Log.d("MainActivity", sch);
                Call<GoogleData> call = googleapicse.getPic(sch, key, cx, "medium", "image", "jpg");

                call.enqueue(new Callback<GoogleData>() {

                    @Override
                    public void onResponse(Call<GoogleData> call, Response<GoogleData> response) {
                        Log.d("MainActivity", "Status Code = " + response.code());
                        Log.d("MainActivity",  "Request string = " + call.request().url().toString());
                        if (response.isSuccessful()) {
                            GoogleData result = response.body();
                            loadPictures(result.getItems());

                        }
                    }

                    @Override
                    public void onFailure(Call<GoogleData> call, Throwable t) {
                        Log.d("MainActivity", t.getMessage());
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void loadPictures(List<Item> pics) {
        ArrayList<String> img_url = new ArrayList<String>();
        for (Item pic: pics) {
            img_url.add(pic.getLink());
        }

        MyRecyleViewAdapter adapter = new MyRecyleViewAdapter(this, img_url);

        mrecyclerview = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new GridLayoutManager(this, 2);
        mrecyclerview.setLayoutManager(mLayoutManager);

        mrecyclerview.setAdapter(adapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
