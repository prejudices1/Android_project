package com.byted.chapter5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MyAdapter.ListItemClickListener{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyAdapter mAdapter;
    private List<ArticleResponse> mArticles;
    private Context context;
    private List<ArticleResponse> articlesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // GsonDemo.parseGsonString();
        //JsonDemo.parseJsonString();

        setTitle("VideoList");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mArticles = new ArrayList<>();
        mAdapter = new MyAdapter((MyAdapter.ListItemClickListener) this);

        recyclerView.setAdapter(mAdapter);
        context = recyclerView.getContext();

        getData();
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getArticles().enqueue(new Callback<List<ArticleResponse>>() {
            @Override
            public void onResponse(Call<List<ArticleResponse>> call, Response<List<ArticleResponse>> response) {
                if (response.body() != null) {
                    articlesList = response.body();
                    //Log.d("retrofit", articles.toString());
                    Log.d("dong",articlesList.get(0).description.toString());
                    if (articlesList.size() != 0) {
                        mAdapter.setData(context,response.body());
                        mAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<ArticleResponse>> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d("dong",clickedItemIndex+"clicked");
        Intent intent = new Intent(this, VideoPlayer.class);
        intent.putExtra("avator",articlesList.get(clickedItemIndex).avatar);
        intent.putExtra("nickname",articlesList.get(clickedItemIndex).nickname);
        intent.putExtra("likecount",articlesList.get(clickedItemIndex).likecount);
        intent.putExtra("description",articlesList.get(clickedItemIndex).description);
        intent.putExtra("feedurl",articlesList.get(clickedItemIndex).feedurl);
        startActivity(intent);
    }
}
