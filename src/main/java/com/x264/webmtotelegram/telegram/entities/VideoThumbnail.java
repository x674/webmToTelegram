package com.x264.webmtotelegram.telegram.entities;

public class VideoThumbnail {
    public VideoThumbnail(String urlVideo, String urlThumbnail) {
        this.urlVideo = urlVideo;
        this.urlThumbnail = urlThumbnail;
    }

    private String urlVideo, urlThumbnail;

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
}
