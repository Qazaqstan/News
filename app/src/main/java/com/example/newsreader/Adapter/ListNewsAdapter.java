package com.example.newsreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newsreader.Common.ISO8601Parse;
import com.example.newsreader.Activity.News;
import com.example.newsreader.Interface.ItemClickListener;
import com.example.newsreader.Model.Article;
import com.example.newsreader.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;

    TextView title;
    RelativeTimeTextView time;
    CircleImageView image;

    public ListNewsViewHolder(View itemView) {
        super(itemView);
        initUI();
        itemView.setOnClickListener(this);
    }

    private void initUI() {
        title = itemView.findViewById(R.id.title);
        time = itemView.findViewById(R.id.time);
        image = itemView.findViewById(R.id.image);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder> implements ItemClickListener {

    private List<Article> articles;
    private Context context;

    public ListNewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ListNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.news_layout, parent, false);
        return new ListNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNewsViewHolder holder, int position) {
        Picasso.with(context)
                .load(articles.get(position).getUrlToImage())
                .into(holder.image);

        holder.title.setText(makeShorterTitle(articles.get(position).getTitle()));

        Date date = parseTime(articles.get(position).getPublishedAt());

        holder.time.setReferenceTime(date.getTime());
        holder.setItemClickListener(this);
    }

    private Date parseTime(String publishedAt) {
        try {
            return ISO8601Parse.parse(publishedAt);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String makeShorterTitle(String title) {
        if (title.length() > 50)
            return title.substring(0, 50) + "...";
        return title;
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Intent intent = new Intent(context, News.class);
        intent.putExtra("newsPageURL", articles.get(position).getUrl());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
