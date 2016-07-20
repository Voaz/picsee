package com.example.voaz.imgtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import android.support.v7.widget.SearchView;

public class ScrollingActivity extends Activity {
    SearchView search;
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
//                try {
//                    //URL url = new URL(String.format("https://www.googleapis.com/customsearch/v1?imgSize=medium&searchType=image&fileType=jpg&q=%s&key=%s&cx=%s", query, key, cx));
//                    URL url = new URL("http://4pda.ru/");
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("GET");
//                    con.setRequestProperty("User-Agent", "Mozilla/5.0");
//
//                    int responseCode = con.getResponseCode(); //вот тут ебошит эксепшн
//                    System.out.println("\nSending 'GET' request to URL : " + url);
//                    System.out.println("Response Code : " + responseCode);
//                    BufferedReader in = new BufferedReader(
//                            new InputStreamReader(con.getInputStream())); //и тут ебошит эксепшн
//                    String inputLine;
//                    StringBuilder response = new StringBuilder();
//
//                    while ((inputLine = in.readLine()) != null) {
//                        response.append(inputLine);
//                    }
//                    in.close();
//
//                    //print result
//                    TextView text = (TextView) findViewById(R.id.textView);
//                    text.setText(response.toString().toCharArray(), 0, response.toString().length());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                //search.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                TextView text = (TextView) findViewById(R.id.textView);
                text.setText(search.getQuery());
                return false;
            }
        });
//        setContentView(R.layout.activity_scrolling);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void loadPictures(List<Item> pics) {
        ImageView img = (ImageView) findViewById(R.id.imageView);

        Picasso.with(this).load(String.valueOf(pics.get(0).getLink())).into(img);
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
