package com.shulg.redditshow.modules;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shulg.redditshow.modules.ThirdAPIs.SubRedditResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shulg on 10/17/17.
 */

public class SubRedditDeserializer implements JsonDeserializer<List<SubReddit>> {
    @Override
    public List<SubReddit> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject body = json.getAsJsonObject();
        List<SubReddit> subReddits = new ArrayList<SubReddit>();

         JsonArray children = body.get("data")
                .getAsJsonObject()
                .get("children").getAsJsonArray();

        for (int i = 0; i < children.size(); i++) {

            JsonObject j = children.get(i).getAsJsonObject().get("data").getAsJsonObject();

            SubReddit sub = new SubReddit();

//            Log.i("SHIT", String.valueOf(j.entrySet().size()));
            sub.title = j.get("title").toString().length() > 2 ? j.get("title").getAsString() : "";
            sub.name = j.get("display_name").toString().length() > 2 ? j.get("display_name").getAsString() : "";
            sub.subscribers = j.has("subscribers") ? j.get("subscribers").getAsInt() : 0;
            sub.searchName = j.get("display_name_prefixed").toString().length() > 2 ? j.get("display_name_prefixed").getAsString() : "";
            sub.iconImage = j.get("icon_img").toString().length() > 8 ? j.get("icon_img").getAsString() : "";
            sub.bannerImage = j.get("banner_img").toString().length() > 8 ? j.get("banner_img").getAsString() : "";
            sub.headerImage = j.get("header_img").toString().length() > 8 ? j.get("header_img").getAsString() : "";
            sub.description = j.get("public_description").toString().length() > 2 ? j.get("public_description").getAsString() : "";

            subReddits.add(sub);
        }

        return subReddits;
    }
}
