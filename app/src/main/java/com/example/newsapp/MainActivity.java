package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Article> list;
    private NewAppAdapter adapter;
    private SearchView searchView;
    private Button button1, button2, button3, button4 ,button5, button6,button7;
    private RecyclerView recyclerView;
    private LinearProgressIndicator progressIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        searchView=findViewById(R.id.search_view);
        recyclerView=findViewById(R.id.recyclerView);
        progressIndicator=findViewById(R.id.progressBar);

        button1=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        button5=findViewById(R.id.button5);
        button6=findViewById(R.id.button6);
        button7=findViewById(R.id.button7);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getNew("general",s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        setUpRecyleView();
        getNew("general",null);
    }
    void changeProges(boolean show){
        if(show){
            progressIndicator.setVisibility(View.VISIBLE);
        }
        else{
            progressIndicator.setVisibility(View.INVISIBLE);
        }
    }
    private void getNew(String category,String query) {
        changeProges(true);
        NewsApiClient newsApiClient= new NewsApiClient("96861785f3f74d4086ffdf3b896fae04");
        newsApiClient.getTopHeadlines(new TopHeadlinesRequest.Builder()
                        .language("en")
                        .category(category)
                        .q(query)
                        .build(), new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                            changeProges(false);
                            list=response.getArticles();
                            adapter.update(list);
                            adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("Error",throwable.getMessage());
                    }
                }
        );

    }

    private void setUpRecyleView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    list=new ArrayList<>();
    adapter=new NewAppAdapter(list);
    recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Button button=(Button) view;
        String category=button.getText().toString();
        getNew(category,null);
    }
}