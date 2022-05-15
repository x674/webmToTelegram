package com.x264.webmtotelegram.telegram.entities;

import com.x264.webmtotelegram.imageboard.dvach.rest.File;

public class TelegramPost {


    private VideoThumbnail videoThumbnail;
    private String threadName;
    private String messageURL;

    private int durationSecs;
    private int height;
    private int width;
    private String fullname;

    public TelegramPost(File file, String threadName, String messageURL) {
        this.videoThumbnail = new VideoThumbnail(file);
        this.threadName = threadName;
        this.messageURL = messageURL;
    }

    public VideoThumbnail getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(VideoThumbnail videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getMessageURL() {
        return messageURL;
    }

    public void setMessageURL(String messageURL) {
        this.messageURL = messageURL;
    }
}
