package com.example.voaz.imgtest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleAPICSE {
    String ENDPOINT = "https://www.googleapis.com";

    @GET("/customsearch/v1")
    Call<GoogleData> getPic(@Query("q") String query, @Query("key") String key, @Query("cx") String cx,
                            @Query("imgSize") String imgSize, @Query("searchType") String searchType,
                            @Query("fileType") String fileType);

    // =medium&searchType=image&fileType=jpg&q={q}&key={key}&cx={cx});
}
