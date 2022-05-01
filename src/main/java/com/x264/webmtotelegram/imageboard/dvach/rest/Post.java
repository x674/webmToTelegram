package com.x264.webmtotelegram.imageboard.dvach.rest;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonProperty;

@Generated("jsonschema2pojo")
public class Post {

    @JsonProperty("banned")
    private Integer banned;
    @JsonProperty("closed")
    private Integer closed;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("date")
    private String date;
    @JsonProperty("email")
    private String email;
    @JsonProperty("endless")
    private Integer endless;
    @JsonProperty("files")
    private List<File> files = new ArrayList<File>();
    @JsonProperty("lasthit")
    private Integer lasthit;
    @JsonProperty("name")
    private String name;
    @JsonProperty("num")
    private Integer num;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("op")
    private Integer op;
    @JsonProperty("parent")
    private String parent;
    @JsonProperty("sticky")
    private Integer sticky;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("tags")
    private String tags;
    @JsonProperty("timestamp")
    private Integer timestamp;
    @JsonProperty("trip")
    private String trip;

    @JsonProperty("banned")
    public Integer getBanned() {
        return banned;
    }

    @JsonProperty("banned")
    public void setBanned(Integer banned) {
        this.banned = banned;
    }

    @JsonProperty("closed")
    public Integer getClosed() {
        return closed;
    }

    @JsonProperty("closed")
    public void setClosed(Integer closed) {
        this.closed = closed;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("endless")
    public Integer getEndless() {
        return endless;
    }

    @JsonProperty("endless")
    public void setEndless(Integer endless) {
        this.endless = endless;
    }

    @JsonProperty("files")
    public List<File> getFiles() {
        return files;
    }

    @JsonProperty("files")
    public void setFiles(List<File> files) {
        this.files = files;
    }

    @JsonProperty("lasthit")
    public Integer getLasthit() {
        return lasthit;
    }

    @JsonProperty("lasthit")
    public void setLasthit(Integer lasthit) {
        this.lasthit = lasthit;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("num")
    public Integer getNum() {
        return num;
    }

    @JsonProperty("num")
    public void setNum(Integer num) {
        this.num = num;
    }

    @JsonProperty("number")
    public Integer getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(Integer number) {
        this.number = number;
    }

    @JsonProperty("op")
    public Integer getOp() {
        return op;
    }

    @JsonProperty("op")
    public void setOp(Integer op) {
        this.op = op;
    }

    @JsonProperty("parent")
    public String getParent() {
        return parent;
    }

    @JsonProperty("parent")
    public void setParent(String parent) {
        this.parent = parent;
    }

    @JsonProperty("sticky")
    public Integer getSticky() {
        return sticky;
    }

    @JsonProperty("sticky")
    public void setSticky(Integer sticky) {
        this.sticky = sticky;
    }

    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @JsonProperty("tags")
    public String getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(String tags) {
        this.tags = tags;
    }

    @JsonProperty("timestamp")
    public Integer getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("trip")
    public String getTrip() {
        return trip;
    }

    @JsonProperty("trip")
    public void setTrip(String trip) {
        this.trip = trip;
    }

}
