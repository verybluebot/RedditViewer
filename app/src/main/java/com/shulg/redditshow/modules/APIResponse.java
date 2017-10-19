package com.shulg.redditshow.modules;

/**
 * Created by shulg on 10/17/17.
 */

public interface APIResponse<T> {
    void callback(T res, int statusCode, String message);
}
