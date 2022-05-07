package com.x264.webmtotelegram.telegram.entities;

import java.util.List;

public class TelegramPost {


    private List<VideoThumbnail> videoThumbnails;
    private String ThreadName;
    private String MessageURL;

    public TelegramPost(List<VideoThumbnail> videoThumbnails, String ThreadName, String MessageURL) {
        this.videoThumbnails = videoThumbnails;
        this.ThreadName = ThreadName;
        this.MessageURL = MessageURL;
    }

    public List<VideoThumbnail> getVideoThumbnails() {
        return videoThumbnails;
    }

    public void setURLVideos(List<VideoThumbnail> videoThumbnails) {
        this.videoThumbnails = videoThumbnails;
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
