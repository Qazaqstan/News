package com.example.newsreader.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IconBetterIdeaClient {

    private static Retrofit instance = null;

    public static Retrofit getClient(String FAVICON_FINDER) {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(FAVICON_FINDER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

}
