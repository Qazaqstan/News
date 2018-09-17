package com.example.newsreader.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.newsreader.Adapter.ListSourceAdapter;
import com.example.newsreader.Common.Common;
import com.example.newsreader.Interface.NewsService;
import com.example.newsreader.Model.WebSite;
import com.example.newsreader.R;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView websites;
    private NewsService service;
    private AlertDialog dialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initPaper();
        initDialog();
        configureService();
        configureSwipeRefreshLayout();
        configureRecyclerView();
        loadDataAndSetDataToAdapter();
    }

    private void initUI() {
        swipeRefreshLayout = findViewById(R.id.refresh);
        websites = findViewById(R.id.websites);
    }

    private void initPaper() {
        Paper.init(this);
    }

    private void initDialog() {
        dialog = new SpotsDialog(this);
    }

    private void configureService() {
        service = Common.getNewsService();
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void configureRecyclerView() {
        websites.setHasFixedSize(true);
        websites.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        service.getSources().enqueue(new Callback<WebSite>() {
            @Override
            public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                setDataToAdapter(response.body());
                Paper.book().write("cache", new Gson().toJson(response.body()));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<WebSite> call, Throwable t) {}
        });
    }

    private void loadDataAndSetDataToAdapter() {
        String cache = Paper.book().read("cache");
        if(hasCache(cache)) {
            loadFromCache(cache);
        }
        else {
            loadFromSite();
        }
    }

    private boolean hasCache(String cache) {
        return cache != null && !cache.isEmpty();
    }

    private void loadFromCache(String cache) {
        WebSite webSite = new Gson().fromJson(cache, WebSite.class);
        setDataToAdapter(webSite);
    }

    private void loadFromSite() {
        dialog.show();
        service.getSources().enqueue(new Callback<WebSite>() {
            @Override
            public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                setDataToAdapter(response.body());
                Paper.book().write("cache", new Gson().toJson(response.body()));
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<WebSite> call, Throwable t) {}
        });
    }

    public void setDataToAdapter(WebSite webSite) {
        ListSourceAdapter adapter = new ListSourceAdapter(getBaseContext(), webSite);
        adapter.notifyDataSetChanged();
        websites.setAdapter(adapter);
    }
}