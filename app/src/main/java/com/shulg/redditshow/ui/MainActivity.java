package com.shulg.redditshow.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.shulg.redditshow.R;
import com.shulg.redditshow.adapters.MainPageAdapter;
import com.shulg.redditshow.api.RedditClient;
import com.shulg.redditshow.modules.APIResponse;
import com.shulg.redditshow.modules.SubReddit;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<SubReddit> _reddits;
    private RecyclerView _mainPageRecycler;
    private MainPageAdapter _mainPageAdaptepter;
    private EditText editTextView;

    private List<SubReddit> test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _mainPageRecycler = (RecyclerView) findViewById(R.id.mainList);
        editTextView =  (EditText) findViewById(R.id.editTextMain);

        _mainPageAdaptepter = new MainPageAdapter(MainActivity.this, _reddits);
        _mainPageRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        _mainPageRecycler.setItemAnimator(new DefaultItemAnimator());

        _mainPageRecycler.setAdapter(_mainPageAdaptepter);


        editTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String searchTerm = editTextView.getText().toString();
                RedditClient.instance().reddits(searchTerm ,new APIResponse<List<SubReddit>>() {
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

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
