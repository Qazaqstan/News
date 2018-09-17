package com.example.newsreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newsreader.Activity.AgencyNews;
import com.example.newsreader.Common.Common;
import com.example.newsreader.Enumeration.Key;
import com.example.newsreader.Enumeration.URL;
import com.example.newsreader.Interface.IconBetterIdeaService;
import com.example.newsreader.Interface.ItemClickListener;
import com.example.newsreader.Model.IconBetterIdea;
import com.example.newsreader.Model.Source;
import com.example.newsreader.Model.WebSite;
import com.example.newsreader.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder> implements Callback<IconBetterIdea>, ItemClickListener {
    private Context context;
    private WebSite webSite;
    private IconBetterIdeaService service;
    private ListSourceViewHolder holder;

    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;
        service = Common.getIconService();
    }

    @NonNull
    @Override
    public ListSourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout, parent, false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSourceViewHolder holder, int position) {
        StringBuilder iconBetterAPI = new StringBuilder(URL.FAVICONFINDERJSON.getAPI());
        iconBetterAPI.append(webSite.getSources().get(position).getUrl());

        this.holder = holder;

        service.getIconUrl(iconBetterAPI.toString())
                .enqueue(this);

        holder.title.setText(webSite.getSources().get(position).getName());
        holder.setItemClickListener(this);
    }

    @Override
    public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
        if (response.body() != null && response.body().getIcons().size() > 0)
            Picasso.with(context)
                    .load(response.body().getIcons().get(0).getUrl())
                    .into(holder.image);
    }
    @Override
    public void onFailure(Call<IconBetterIdea> call, Throwable t) {

    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Intent intent = new Intent(context, AgencyNews.class);
        intent.putExtra("source", webSite.getSources().get(position).getId());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        List<Source> sources = webSite.getSources();
        return sources == null ? 0 : sources.size();
    }
}

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickListener itemClickListener;
    TextView title;
    CircleImageView image;


    public ListSourceViewHolder(View itemView) {
        super(itemView);
        initUI();
        itemView.setOnClickListener(this);
    }

    private void initUI() {
        title = itemView.findViewById(R.id.name);
        image = itemView.findViewById(R.id.image);
    }

    public void setItemClickListener (ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

}
