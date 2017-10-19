package com.shulg.redditshow.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shulg.redditshow.BuildConfig;
import com.shulg.redditshow.modules.APIResponse;
import com.shulg.redditshow.modules.SubReddit;
import com.shulg.redditshow.modules.SubRedditDeserializer;
import com.shulg.redditshow.modules.ThirdAPIs.SubRedditResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by shulg on 10/16/17.
 */

public class RedditClient {

    public final String baseUrl;
    private static RedditClient _instance = null;
    private final Retrofit _retrofit;

    public static RedditClient instance() {
        if (_instance == null) {
            _instance = new RedditClient();
        }

        return _instance;
    }

    RedditClient() {
        baseUrl = "https://www.reddit.com/";

        OkHttpClient client = createClientWithInterceptors();

        // create GSON deserializer
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(SubRedditResponse.class, new SubRedditDeserializer());
        Gson myGson = gsonBuilder.create();

        _retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(myGson))
                .build();

    }

    private OkHttpClient createClientWithInterceptors() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        // adding logging
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.BASIC);

            client.addInterceptor(logger);
        }

        return client.build();
    }

    // http requests
    public void reddits(final APIResponse<List<SubReddit>> apiResponse) {
        _retrofit.create(Reddits.class).reddits().enqueue(new Callback<SubRedditResponse>() {

            @Override
            public void onResponse(Call<SubRedditResponse> call, Response<SubRedditResponse> response) {

                // really really not sure this is not a big no no
                @SuppressWarnings("unchecked")
                List<SubReddit> reddits = (List<SubReddit>) response.body();
                apiResponse.callback(reddits , response.code(), response.message());

            }

            @Override
            public void onFailure(Call<SubRedditResponse> call, Throwable t) {
                apiResponse.callback(null, 500, t.getMessage());
                Log.e("ERROR ON HTTP RESPONSE", t.toString());
            }
        });

    }


    // end points
    public interface Reddits {
        @GET("reddits.json?limit=30")
        Call<SubRedditResponse> reddits();
    }
}
