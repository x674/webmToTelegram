package com.x264.webmtotelegram.imageboard.fourchan.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {

    @JsonProperty("no")
    private Integer no;
    @JsonProperty("now")
    private String now;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sub")
    private String sub;
    @JsonProperty("com")
    private String com;
    @JsonProperty("filename")
    private String filename;
    @JsonProperty("ext")
    private String ext;
    @JsonProperty("w")
    private Integer w;
    @JsonProperty("h")
    private Integer h;
    @JsonProperty("tn_w")
    private Integer tnW;
    @JsonProperty("tn_h")
    private Integer tnH;
    @JsonProperty("tim")
    private Long tim;
    @JsonProperty("time")
    private Integer time;
    @JsonProperty("md5")
    private String md5;
    @JsonProperty("fsize")
    private Integer fsize;
    @JsonProperty("resto")
    private Integer resto;
    @JsonProperty("bumplimit")
    private Integer bumplimit;
    @JsonProperty("imagelimit")
    private Integer imagelimit;
    @JsonProperty("semantic_url")
    private String semanticUrl;
    @JsonProperty("replies")
    private Integer replies;
    @JsonProperty("images")
    private Integer images;
    @JsonProperty("unique_ips")
    private Integer uniqueIps;
    @JsonProperty("tail_size")
    private Integer tailSize;

    @JsonProperty("no")
    public Integer getNo() {
        return no;
    }

    @JsonProperty("no")
    public void setNo(Integer no) {
        this.no = no;
    }

    @JsonProperty("now")
    public String getNow() {
        return now;
    }

    @JsonProperty("now")
    public void setNow(String now) {
        this.now = now;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("sub")
    public String getSub() {
        return sub;
    }

    @JsonProperty("sub")
    public void setSub(String sub) {
        this.sub = sub;
    }

    @JsonProperty("com")
    public String getCom() {
        return com;
    }

    @JsonProperty("com")
    public void setCom(String com) {
        this.com = com;
    }

    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    @JsonProperty("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @JsonProperty("ext")
    public String getExt() {
        return ext;
    }

    @JsonProperty("ext")
    public void setExt(String ext) {
        this.ext = ext;
    }

    @JsonProperty("w")
    public Integer getW() {
        return w;
    }

    @JsonProperty("w")
    public void setW(Integer w) {
        this.w = w;
    }

    @JsonProperty("h")
    public Integer getH() {
        return h;
    }

    @JsonProperty("h")
    public void setH(Integer h) {
        this.h = h;
    }

    @JsonProperty("tn_w")
    public Integer getTnW() {
        return tnW;
    }

    @JsonProperty("tn_w")
    public void setTnW(Integer tnW) {
        this.tnW = tnW;
    }

    @JsonProperty("tn_h")
    public Integer getTnH() {
        return tnH;
    }

    @JsonProperty("tn_h")
    public void setTnH(Integer tnH) {
        this.tnH = tnH;
    }

    @JsonProperty("tim")
    public Long getTim() {
        return tim;
    }

    @JsonProperty("tim")
    public void setTim(Long tim) {
        this.tim = tim;
    }

    @JsonProperty("time")
    public Integer getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Integer time) {
        this.time = time;
    }

    @JsonProperty("md5")
    public String getMd5() {
        return md5;
    }

    @JsonProperty("md5")
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @JsonProperty("fsize")
    public Integer getFsize() {
        return fsize;
    }

    @JsonProperty("fsize")
    public void setFsize(Integer fsize) {
        this.fsize = fsize;
    }

    @JsonProperty("resto")
    public Integer getResto() {
        return resto;
    }

    @JsonProperty("resto")
    public void setResto(Integer resto) {
        this.resto = resto;
    }

    @JsonProperty("bumplimit")
    public Integer getBumplimit() {
        return bumplimit;
    }

    @JsonProperty("bumplimit")
    public void setBumplimit(Integer bumplimit) {
        this.bumplimit = bumplimit;
    }

    @JsonProperty("imagelimit")
    public Integer getImagelimit() {
        return imagelimit;
    }

    @JsonProperty("imagelimit")
    public void setImagelimit(Integer imagelimit) {
        this.imagelimit = imagelimit;
    }

    @JsonProperty("semantic_url")
    public String getSemanticUrl() {
        return semanticUrl;
    }

    @JsonProperty("semantic_url")
    public void setSemanticUrl(String semanticUrl) {
        this.semanticUrl = semanticUrl;
    }

    @JsonProperty("replies")
    public Integer getReplies() {
        return replies;
    }

    @JsonProperty("replies")
    public void setReplies(Integer replies) {
        this.replies = replies;
    }

    @JsonProperty("images")
    public Integer getImages() {
        return images;
    }

    @JsonProperty("images")
    public void setImages(Integer images) {
        this.images = images;
    }

    @JsonProperty("unique_ips")
    public Integer getUniqueIps() {
        return uniqueIps;
    }

    @JsonProperty("unique_ips")
    public void setUniqueIps(Integer uniqueIps) {
        this.uniqueIps = uniqueIps;
    }

    @JsonProperty("tail_size")
    public Integer getTailSize() {
        return tailSize;
    }

    @JsonProperty("tail_size")
    public void setTailSize(Integer tailSize) {
        this.tailSize = tailSize;
    }

}
