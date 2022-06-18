package com.x264.webmtotelegram.imageboard.fourchan;


import com.x264.webmtotelegram.imageboard.fourchan.rest.Post;
import com.x264.webmtotelegram.imageboard.fourchan.rest.Thread;

public class URLBuilder {
    private URLBuilder() {
    }

    public final static String FILE_URL = "https://i.4cdn.org/";

    public static String buildMediaURL(String board, String filename, String ext) {
        return FILE_URL + board + filename + ext;
    }

    public static String buildThumbnailURL(String board, String filename) {
        return FILE_URL + board + filename + "s.jpg";
    }

    public static String buildPostURL(String board, Thread thread, Post post) {
        return FourchanRequests.HOSTNAME + board + "/thread/" + thread.getNo() + "#p" + post.getNo();
    }

}