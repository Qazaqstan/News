package com.example.newsreader.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.newsreader.Adapter.ListNewsAdapter;
import com.example.newsreader.Common.Common;
import com.example.newsreader.Interface.NewsService;
import com.example.newsreader.Model.Article;
import com.example.newsreader.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgencyNews extends AppCompatActivity implements Callback<com.example.newsreader.Model.News>, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private KenBurnsView kenBurnsView;
    private DiagonalLayout diagonalLayout;
    private AlertDialog dialog;
    private NewsService service;
    private TextView author, title;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String source = "";
    private String newsPageURL = "";

    private RecyclerView news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
        service = Common.getNewsService();

        initUI();
        initListeners();
        configureRecyclerView();
        if (hasIntent()) {
            source = getIntent().getStringExtra("source");
            loadNews();
        }
    }

    private void initUI() {
        dialog = new SpotsDialog(this);
        swipeRefreshLayout = findViewById(R.id.refresh);
        diagonalLayout = findViewById(R.id.diagonalLayout);
        kenBurnsView = findViewById(R.id.image);
        author = findViewById(R.id.author);
        title = findViewById(R.id.title);
        news = findViewById(R.id.news);
    }

    private void initListeners() {
        swipeRefreshLayout.setOnRefreshListener(this);
        diagonalLayout.setOnClickListener(this);
    }

    private void configureRecyclerView() {
        news.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        news.setLayoutManager(layoutManager);
    }

    private boolean hasIntent() {
        return getIntent() != null && !getIntent().getStringExtra("source").isEmpty();
    }

    @Override
    public void onRefresh() {
        loadNews();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), News.class);
        intent.putExtra("newsPageURL", newsPageURL);
        startActivity(intent);
    }

    private void loadNews() {
        dialog.show();
        service.getNewestArticles(Common.getAPIUrl(source))
                .enqueue(this);
    }

    @Override
    public void onResponse(Call<com.example.newsreader.Model.News> call, Response<com.example.newsreader.Model.News> response) {
        if (response.body() != null && response.body().getArticles().size() > 0) {
            setDataToTopView(response.body().getArticles().get(0));
            setDataToRecyclerView(response.body().getArticles());
            newsPageURL = response.body().getArticles().get(0).getUrl();
        }
        dialog.dismiss();
    }

    @Override
    public void onFailure(Call<com.example.newsreader.Model.News> call, Throwable t) {}

    public void setDataToTopView(Article dataToKenBurnsView) {
        Picasso.with(getBaseContext())
                .load(dataToKenBurnsView.getUrlToImage())
                .into(kenBurnsView);
        title.setText(dataToKenBurnsView.getTitle());
        author.setText(dataToKenBurnsView.getAuthor());
    }

    public void setDataToRecyclerView(List<Article> list) {
        ListNewsAdapter adapter = new ListNewsAdapter(list.subList(1, list.size() - 1), getBaseContext());
        adapter.notifyDataSetChanged();
        news.setAdapter(adapter);
    }
}
