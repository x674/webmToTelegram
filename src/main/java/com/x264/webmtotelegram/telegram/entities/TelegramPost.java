package com.x264.webmtotelegram.telegram.entities;

import java.util.List;

public class TelegramPost {
    private List<String> URLVideos;
    private String ThreadName;
    private String MessageURL;

    public TelegramPost(List<String> URLVideos, String ThreadName, String MessageURL) {
        this.URLVideos = URLVideos;
        this.ThreadName = ThreadName;
        this.MessageURL = MessageURL;
    }

    public List<String> getURLVideos() {
        return URLVideos;
    }

    public void setURLVideos(List<String> URLVideos) {
        this.URLVideos = URLVideos;
    }

    public String getThreadName() {
        return ThreadName;
    }

    public void setThreadName(String threadName) {
        ThreadName = threadName;
    }

    public String getMessageURL() {
        return MessageURL;
    }

    public void setMessageURL(String messageURL) {
        MessageURL = messageURL;
    }
}
