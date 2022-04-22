package com.x264.webmtotelegram.ImageBoard.Dvach.Rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class File {

    @JsonProperty("displayname")
    private String displayname;
    @JsonProperty("fullname")
    private String fullname;
    @JsonProperty("height")
    private Integer height;
    @JsonProperty("md5")
    private String md5;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nsfw")
    private Integer nsfw;
    @JsonProperty("path")
    private String path;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("tn_height")
    private Integer tnHeight;
    @JsonProperty("tn_width")
    private Integer tnWidth;
    @JsonProperty("type")
    private long type;
    @JsonProperty("width")
    private Integer width;
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("duration_secs")
    private Integer durationSecs;
    @JsonProperty("install")
    private String install;
    @JsonProperty("pack")
    private String pack;
    @JsonProperty("sticker")
    private String sticker;

    @JsonProperty("displayname")
    public String getDisplayname() {
        return displayname;
    }

    @JsonProperty("displayname")
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    @JsonProperty("fullname")
    public String getFullname() {
        return fullname;
    }

    @JsonProperty("fullname")
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @JsonProperty("height")
    public Integer getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(Integer height) {
        this.height = height;
    }

    @JsonProperty("md5")
    public String getMd5() {
        return md5;
    }

    @JsonProperty("md5")
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("nsfw")
    public Integer getNsfw() {
        return nsfw;
    }

    @JsonProperty("nsfw")
    public void setNsfw(Integer nsfw) {
        this.nsfw = nsfw;
    }

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    @JsonProperty("thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }

    @JsonProperty("thumbnail")
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @JsonProperty("tn_height")
    public Integer getTnHeight() {
        return tnHeight;
    }

    @JsonProperty("tn_height")
    public void setTnHeight(Integer tnHeight) {
        this.tnHeight = tnHeight;
    }

    @JsonProperty("tn_width")
    public Integer getTnWidth() {
        return tnWidth;
    }

    @JsonProperty("tn_width")
    public void setTnWidth(Integer tnWidth) {
        this.tnWidth = tnWidth;
    }

    @JsonProperty("type")
    public long getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(long type) {
        this.type = type;
    }

    @JsonProperty("width")
    public Integer getWidth() {
        return width;
    }

    @JsonProperty("width")
    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty("duration_secs")
    public Integer getDurationSecs() {
        return durationSecs;
    }

    @JsonProperty("duration_secs")
    public void setDurationSecs(Integer durationSecs) {
        this.durationSecs = durationSecs;
    }

    @JsonProperty("install")
    public String getInstall() {
        return install;
    }

    @JsonProperty("install")
    public void setInstall(String install) {
        this.install = install;
    }

    @JsonProperty("pack")
    public String getPack() {
        return pack;
    }

    @JsonProperty("pack")
    public void setPack(String pack) {
        this.pack = pack;
    }

    @JsonProperty("sticker")
    public String getSticker() {
        return sticker;
    }

    @JsonProperty("sticker")
    public void setSticker(String sticker) {
        this.sticker = sticker;
    }

}
