package com.example.newsreader.Common;

import com.example.newsreader.Enumeration.Key;
import com.example.newsreader.Enumeration.URL;
import com.example.newsreader.Interface.IconBetterIdeaService;
import com.example.newsreader.Interface.NewsService;
import com.example.newsreader.Remote.IconBetterIdeaClient;
import com.example.newsreader.Remote.NewsClient;

public class Common {

    public static NewsService getNewsService () {
        return NewsClient.getClient(URL.NEWSAPI.getAPI()).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService() {
        return IconBetterIdeaClient.getClient(URL.FAVICONFINDER.getAPI()).create(IconBetterIdeaService.class);
    }

    public static String getAPIUrl(String source) {
        return new StringBuilder(URL.NEWSAPIVERSION.getAPI())
                .append("everything?sources=")
                .append(source)
                .append("&apiKey=")
                .append(Key.NEWSAPI)
                .toString();
    }
}