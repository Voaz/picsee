package com.example.voaz.imgtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pojoobj.GoogleData;
import pojoobj.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import android.support.v7.widget.SearchView;

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
                //String key = "AIzaSyAKxETwEuML8_k8kTYQlULkqN8M8I8xVqk";
                String key = "AIzaSyAKxETwEuML8_k8kTYQlULkqN8M8I8xVqk";
                //String cx  = "016873079342806567471%3A0fgymbpophy";
                CharSequence cx  = "016873079342806567471:0fgymbpophy";

//                Gson gson = new GsonBuilder()
//                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(GoogleAPICSE.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GoogleAPICSE googleapicse = retrofit.create(GoogleAPICSE.class);
                String sch = search.getQuery().toString();
                Log.d("MainActivity", sch);
                Call<GoogleData> call = googleapicse.getPic(sch, key, cx.toString(), "medium", "image", "jpg");

                call.enqueue(new Callback<GoogleData>() {

                    @Override
                    public void onResponse(Call<GoogleData> call, Response<GoogleData> response) {
                        Log.d("MainActivity", "Status Code = " + response.code());
                        Log.d("MainActivity",  "Request string = " + call.request().url().toString());
                        if (response.isSuccessful()) {
                            GoogleData result = response.body();
                            Log.d("MainActivity", "Zapros proSHEL!!!");
                            Log.d("MainActivity", String.valueOf(response.body().getItems().get(0).getLink()));
                            TextView text = (TextView) findViewById(R.id.textView);
                            text.setText(String.valueOf(response.body().getItems().get(0).getLink()));

                            loadPictures(result.getItems());

                        }
                    }

                    @Override
                    public void onFailure(Call<GoogleData> call, Throwable t) {
                        Log.d("MainActivity", "Status Code 666! Vse poshlo po pizde");
                        Log.d("MainActivity", t.getMessage());
                    }
                });
//
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                TextView text = (TextView) findViewById(R.id.textView);
                text.setText(search.getQuery());
                return false;
            }
        });
    }

    public void loadPictures(List<Item> pics) {
        ImageView img = (ImageView) findViewById(R.id.imageView);

        Picasso.with(this).load(String.valueOf(pics.get(0).getLink())).into(img);

        mrecyclerview = (RecyclerView) findViewById(R.id.recycleView);

        mLayoutManager = new LinearLayoutManager(this);
        mrecyclerview.setLayoutManager(mLayoutManager);
        ArrayList<String> img_url = null;
        for (Item pic: pics) {
            img_url.add(pic.getLink());
        }

       MyRecyleViewAdapter adapter = new MyRecyleViewAdapter(img_url);
        mrecyclerview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_scrolling, menu);

        //final SearchView search = (SearchView) menu.findItem(R.id.searchView).getActionView();



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
