package com.x264.webmtotelegram.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "threads")
@Entity
public class ImageBoardThread {
    @Id
    private long idThread;
    @Basic
    private String title;
    @Basic
    private long lastCheckedMessage = 0;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "threads_mediaFiles",
            joinColumns = @JoinColumn(name = "ImageBoardThread_idThread", referencedColumnName = "idThread"),
            inverseJoinColumns = @JoinColumn(name = "mediaFiles_md5", referencedColumnName = "md5"))
    private List<MediaFile> mediaFiles = new ArrayList<>();

    public void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void addMediaFile(MediaFile mediaFile) {
        this.mediaFiles.add(mediaFile);
    }

    public long getIdThread() {
        return idThread;
    }

    public void setIdThread(long idThread) {
        this.idThread = idThread;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getLastCheckedMessage() {
        return lastCheckedMessage;
    }

    public void setLastCheckedMessage(long lastCheckedMessage) {
        this.lastCheckedMessage = lastCheckedMessage;
    }

}
