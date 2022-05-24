package com.x264.webmtotelegram.telegram.entities;

import java.util.Objects;
import java.util.Optional;

import com.x264.webmtotelegram.imageboard.dvach.URLBuilder;
import com.x264.webmtotelegram.imageboard.dvach.rest.File;

public class VideoThumbnail {
    public VideoThumbnail(File file) {
        try {
            this.urlThumbnail = URLBuilder.buildThumbnailURL(file);
            this.urlVideo = URLBuilder.buildMediaURL(file);
            this.filename = file.getFullname();
            if (file.getDurationSecs().isPresent())
                this.durationSecs = file.getDurationSecs().get();
            this.md5 = file.getMd5();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public Optional<Integer> getDurationSecs() {
        if (Objects.isNull(this.durationSecs))
            return Optional.empty();
        return Optional.of(this.durationSecs);
    }

    public void setDurationSecs(int durationSecs) {
        this.durationSecs = durationSecs;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VideoThumbnail videoThumbnail = (VideoThumbnail)o;

        return getMd5() != null 
            ?getMd5().equals(videoThumbnail.getMd5())
            :videoThumbnail.getMd5() == null;

    }

}
