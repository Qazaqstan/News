package com.example.newsreader.Enumeration;

public enum URL {

    NEWSAPI("https://newsapi.org/"),
    NEWSAPIVERSION(NEWSAPI.getAPI() + "v2/"),
    FAVICONFINDER("https://besticon-demo.herokuapp.com/"),
    FAVICONFINDERJSON(FAVICONFINDER.getAPI() + "allicons.json?url=");

    private String api;

    URL(String api) {
        this.api = api;
    }

    public String getAPI() {
        return this.api;
    }

}
