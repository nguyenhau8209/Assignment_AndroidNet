package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("planetary/apod")
    Call<NasaApodResponse> getNasaApod(@Query("api_key") String apiKey, @Query("date") String date);
}

