package com.x264.webmtotelegram.imageboard.dvach;

import com.x264.webmtotelegram.imageboard.dvach.rest.File;
import com.x264.webmtotelegram.imageboard.dvach.rest.Post;
import com.x264.webmtotelegram.imageboard.dvach.rest.Thread;

public class URLBuilder {
    private URLBuilder() {
    }

    public static String buildMediaURL(File file) {
        return Requests.HOSTNAME + file.getPath();
    }

    public static String buildThumbnailURL(File file) {
        return Requests.HOSTNAME + file.getThumbnail();
    }


    public static String buildPostURL(String board, Thread thread, Post post) {
        return Requests.HOSTNAME + "/" + board + "/res/" + thread.getNum() + ".html" + "#" + post.getNum();
    }

}
