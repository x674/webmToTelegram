package com.x264.webmtotelegram.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "threads")
@Entity
public class ImageBoardThread {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private long id;
    @Basic
    private long idThread;
    @Basic
    private String board;
    @Basic
    private String title;
    @Basic
    private long lastCheckedMessage = 0;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "threads_mediaFiles",
            joinColumns = @JoinColumn(name = "ImageBoardThread_idThread", referencedColumnName = "idThread"),
            inverseJoinColumns = @JoinColumn(name = "mediaFiles_md5", referencedColumnName = "md5"))
    private List<MediaFile> mediaFiles = new ArrayList<>();

    public void addMediaFile(MediaFile mediaFile) {
        this.mediaFiles.add(mediaFile);
    }
    /**
     * @return long return the idThread
     */
    public long getIdThread() {
        return idThread;
    }

    /**
     * @param idThread the idThread to set
     */
    public void setIdThread(long idThread) {
        this.idThread = idThread;
    }

    /**
     * @return String return the board
     */
    public String getBoard() {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(String board) {
        this.board = board;
    }

    /**
     * @return String return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return long return the lastCheckedMessage
     */
    public long getLastCheckedMessage() {
        return lastCheckedMessage;
    }

    /**
     * @param lastCheckedMessage the lastCheckedMessage to set
     */
    public void setLastCheckedMessage(long lastCheckedMessage) {
        this.lastCheckedMessage = lastCheckedMessage;
    }

    /**
     * @return List<MediaFile> return the mediaFiles
     */
    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    /**
     * @param mediaFiles the mediaFiles to set
     */
    public void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

}
