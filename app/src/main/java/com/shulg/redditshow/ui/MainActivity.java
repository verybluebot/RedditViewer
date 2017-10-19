package com.shulg.redditshow.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.shulg.redditshow.R;
import com.shulg.redditshow.adapters.MainPageAdapter;
import com.shulg.redditshow.api.RedditClient;
import com.shulg.redditshow.modules.APIResponse;
import com.shulg.redditshow.modules.SubReddit;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<SubReddit> _reddits;
    private RecyclerView _mainPageRecycler;
    private MainPageAdapter _mainPageAdaptepter;

    private List<SubReddit> test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _mainPageRecycler = (RecyclerView) findViewById(R.id.mainList);

        _mainPageAdaptepter = new MainPageAdapter(MainActivity.this, _reddits);
        _mainPageRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        _mainPageRecycler.setItemAnimator(new DefaultItemAnimator());

        _mainPageRecycler.setAdapter(_mainPageAdaptepter);

        RedditClient.instance().reddits(new APIResponse<List<SubReddit>>() {
            @Override
            public void callback(List<SubReddit> res, int statusCode, String message) {
                if (statusCode != 200) {
                    return;
                }
                _reddits = res;

                _mainPageAdaptepter.setSubReddits(_reddits);
                _mainPageAdaptepter.notifyDataSetChanged();
            }
        });
    }
}
