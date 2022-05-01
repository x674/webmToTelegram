package com.x264.webmtotelegram.imageboard.dvach;

import com.x264.webmtotelegram.imageboard.dvach.rest.File;
import com.x264.webmtotelegram.imageboard.dvach.rest.Post;
import com.x264.webmtotelegram.imageboard.dvach.rest.Thread;

public class URLBuilder {
    private URLBuilder(){}
    
    public static String buildMediaURL(Thread thread, File file){
        return Requests.HOSTNAME + "/b"+ thread.getBoard() +"/res/" + file.getPath();
    }
    

    public static String buildPostURL(Thread thread, Post post){
        return Requests.HOSTNAME + "/b/res/" + thread.getNum()+ ".html" + "#" + post.getNum();
    }
    
}
