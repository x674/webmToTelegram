package com.x264.webmtotelegram.entities;

import javax.persistence.*;

@Entity
@Table(name = "media_file")
public class MediaFile {

    @Id
    private String md5;
    @Basic
    private String name;
    @Basic
    private boolean uploaded;

    public MediaFile(String md5, String name) {
        this.md5 = md5;
        this.name = name;
    }

    public MediaFile() {

    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}