package com.example.newsapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewAppAdapter extends RecyclerView.Adapter<NewAppAdapter.ViewHolder> {
    List<Article> list;

    public NewAppAdapter(List<Article> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NewAppAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsapp,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewAppAdapter.ViewHolder holder, int position) {
        Article article=list.get(position);

        Picasso.get().load(article.getUrlToImage()).into(holder.imageView);
        holder.title.setText(article.getTitle());
        holder.source.setText(article.getSource().getName());
        holder.author.setText(article.getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),NewsFullActivity.class);
                intent.putExtra("url",article.getUrl());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<Article>data) {
        list.clear();
        list.addAll(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,source,author;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            source=itemView.findViewById(R.id.source);
            author=itemView.findViewById(R.id.author);
            imageView=itemView.findViewById(R.id.imageView2);
        }
    }
}
