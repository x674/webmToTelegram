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
    private String fullName;

    public MediaFile(String md5, String name, String fullName) {
        this.md5 = md5;
        this.name = name;
        this.fullName = fullName;
    }

    public MediaFile() {
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}