package com.example.newsreader.Interface;

import com.example.newsreader.Common.Common;
import com.example.newsreader.Enumeration.Key;
import com.example.newsreader.Model.News;
import com.example.newsreader.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsService {

    @GET("v2/sources?apiKey=" + Key.NEWSAPI)
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);

}
