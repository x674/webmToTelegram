package com.x264.webmtotelegram.imageboard.fourchan.rest;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ThreadPosts {

    @JsonProperty("posts")
    private List<Post> posts = new ArrayList<Post>();

    @JsonProperty("posts")
    public List<Post> getPosts() {
        return posts;
    }

    @JsonProperty("posts")
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
