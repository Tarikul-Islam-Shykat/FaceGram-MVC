package com.example.facegram2.Explore.Api;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //full url : https://newsapi.org/v2/top-headlines?country=us&apiKey=e95b2045a3b0499fb26a71b16ce59e78
    String BASE_URL = "https://newsapi.org/v2/";

    @GET("top-headlines")
    Call<MainNewModel> getNews(
            @Query("country") String country,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );

    // for categoryNews GET https://newsapi.org/v2/top-headlines/sources?category=businessapiKey=API_KEY
    @GET("top-headlines")
    Call<MainNewModel> getCategoryNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );


}
