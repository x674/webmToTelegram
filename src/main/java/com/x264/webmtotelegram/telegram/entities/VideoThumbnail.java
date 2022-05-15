package com.x264.webmtotelegram.telegram.entities;

import com.x264.webmtotelegram.imageboard.dvach.URLBuilder;
import com.x264.webmtotelegram.imageboard.dvach.rest.File;

public class VideoThumbnail {
    public VideoThumbnail(File file) {
        this.urlThumbnail = URLBuilder.buildThumbnailURL(file);
        this.urlVideo = URLBuilder.buildMediaURL(file);
        this.filename = file.getFullname();
        this.durationSecs = file.getDurationSecs();
        this.height = file.getHeight();
        this.width = file.getWidth();
        this.md5 = file.getMd5();
    }

    private String urlVideo;
    private String urlThumbnail;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    private String md5;
    private String filename;
    private int durationSecs;
    private int height;
    private int width;

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getDurationSecs() {
        return durationSecs;
    }

    public void setDurationSecs(int durationSecs) {
        this.durationSecs = durationSecs;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
