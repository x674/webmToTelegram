package com.x264.webmtotelegram.ImageBoard;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Thread {
    @Id
    @JsonProperty("current_thread")
    private long idThread;
    @Basic
    private String title;
    @Basic
    private long lastCheckedMessage = 0;

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
