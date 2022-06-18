package com.x264.webmtotelegram.imageboard.fourchan.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Thread {

    @JsonProperty("no")
    private Integer no;
    @JsonProperty("last_modified")
    private Integer lastModified;
    @JsonProperty("replies")
    private Integer replies;

    @JsonProperty("no")
    public Integer getNo() {
        return no;
    }

    @JsonProperty("no")
    public void setNo(Integer no) {
        this.no = no;
    }

    @JsonProperty("last_modified")
    public Integer getLastModified() {
        return lastModified;
    }

    @JsonProperty("last_modified")
    public void setLastModified(Integer lastModified) {
        this.lastModified = lastModified;
    }

    @JsonProperty("replies")
    public Integer getReplies() {
        return replies;
    }

    @JsonProperty("replies")
    public void setReplies(Integer replies) {
        this.replies = replies;
    }

}
