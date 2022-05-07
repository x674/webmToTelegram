package com.x264.webmtotelegram.telegram.entities;

import com.x264.webmtotelegram.imageboard.dvach.rest.File;

import java.util.List;
import java.util.stream.Collectors;

public class TelegramPost {


    private List<VideoThumbnail> videoThumbnails;
    private String threadName;
    private String messageURL;

    private int durationSecs;
    private int height;
    private int width;
    private String fullname;

    public TelegramPost(List<File> files, String threadName, String messageURL) {
        this.videoThumbnails = files.stream().map(VideoThumbnail::new).collect(Collectors.toList());
        this.threadName = threadName;
        this.messageURL = messageURL;
    }

    public List<VideoThumbnail> getVideoThumbnails() {
        return videoThumbnails;
    }

    public void setURLVideos(List<VideoThumbnail> videoThumbnails) {
        this.videoThumbnails = videoThumbnails;
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
