package com.ogalo.partympakache.wanlive.data;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {

    public String uid;
    public String status;
    public String rating;
    public String title;
    public String cost;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String status, String title, String cost, String rating) {

        this.uid = uid;


        this.status = status;
        this.title = title;
        this.cost = cost;
        this.rating=rating;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("uid", uid);
        result.put("status", status);
        result.put("title", title);

        result.put("cost", cost);
        result.put("rating", rating);
        result.put("starCount", starCount);
        result.put("stars", stars);


        return result;
    }

}