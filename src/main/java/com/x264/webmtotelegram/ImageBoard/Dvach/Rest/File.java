package com.x264.webmtotelegram.ImageBoard.Dvach.Rest;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "displayname",
    "fullname",
    "height",
    "md5",
    "name",
    "nsfw",
    "path",
    "size",
    "thumbnail",
    "tn_height",
    "tn_width",
    "type",
    "width",
    "duration",
    "duration_secs"
})
@Generated("jsonschema2pojo")
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
    private Integer type;
    @JsonProperty("width")
    private Integer width;
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("duration_secs")
    private Integer durationSecs;

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
    public Integer getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Integer type) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(File.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("displayname");
        sb.append('=');
        sb.append(((this.displayname == null)?"<null>":this.displayname));
        sb.append(',');
        sb.append("fullname");
        sb.append('=');
        sb.append(((this.fullname == null)?"<null>":this.fullname));
        sb.append(',');
        sb.append("height");
        sb.append('=');
        sb.append(((this.height == null)?"<null>":this.height));
        sb.append(',');
        sb.append("md5");
        sb.append('=');
        sb.append(((this.md5 == null)?"<null>":this.md5));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("nsfw");
        sb.append('=');
        sb.append(((this.nsfw == null)?"<null>":this.nsfw));
        sb.append(',');
        sb.append("path");
        sb.append('=');
        sb.append(((this.path == null)?"<null>":this.path));
        sb.append(',');
        sb.append("size");
        sb.append('=');
        sb.append(((this.size == null)?"<null>":this.size));
        sb.append(',');
        sb.append("thumbnail");
        sb.append('=');
        sb.append(((this.thumbnail == null)?"<null>":this.thumbnail));
        sb.append(',');
        sb.append("tnHeight");
        sb.append('=');
        sb.append(((this.tnHeight == null)?"<null>":this.tnHeight));
        sb.append(',');
        sb.append("tnWidth");
        sb.append('=');
        sb.append(((this.tnWidth == null)?"<null>":this.tnWidth));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("width");
        sb.append('=');
        sb.append(((this.width == null)?"<null>":this.width));
        sb.append(',');
        sb.append("duration");
        sb.append('=');
        sb.append(((this.duration == null)?"<null>":this.duration));
        sb.append(',');
        sb.append("durationSecs");
        sb.append('=');
        sb.append(((this.durationSecs == null)?"<null>":this.durationSecs));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.thumbnail == null)? 0 :this.thumbnail.hashCode()));
        result = ((result* 31)+((this.nsfw == null)? 0 :this.nsfw.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.tnWidth == null)? 0 :this.tnWidth.hashCode()));
        result = ((result* 31)+((this.duration == null)? 0 :this.duration.hashCode()));
        result = ((result* 31)+((this.path == null)? 0 :this.path.hashCode()));
        result = ((result* 31)+((this.tnHeight == null)? 0 :this.tnHeight.hashCode()));
        result = ((result* 31)+((this.size == null)? 0 :this.size.hashCode()));
        result = ((result* 31)+((this.durationSecs == null)? 0 :this.durationSecs.hashCode()));
        result = ((result* 31)+((this.displayname == null)? 0 :this.displayname.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.width == null)? 0 :this.width.hashCode()));
        result = ((result* 31)+((this.fullname == null)? 0 :this.fullname.hashCode()));
        result = ((result* 31)+((this.height == null)? 0 :this.height.hashCode()));
        result = ((result* 31)+((this.md5 == null)? 0 :this.md5 .hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof File) == false) {
            return false;
        }
        File rhs = ((File) other);
        return ((((((((((((((((this.thumbnail == rhs.thumbnail)||((this.thumbnail!= null)&&this.thumbnail.equals(rhs.thumbnail)))&&((this.nsfw == rhs.nsfw)||((this.nsfw!= null)&&this.nsfw.equals(rhs.nsfw))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.tnWidth == rhs.tnWidth)||((this.tnWidth!= null)&&this.tnWidth.equals(rhs.tnWidth))))&&((this.duration == rhs.duration)||((this.duration!= null)&&this.duration.equals(rhs.duration))))&&((this.path == rhs.path)||((this.path!= null)&&this.path.equals(rhs.path))))&&((this.tnHeight == rhs.tnHeight)||((this.tnHeight!= null)&&this.tnHeight.equals(rhs.tnHeight))))&&((this.size == rhs.size)||((this.size!= null)&&this.size.equals(rhs.size))))&&((this.durationSecs == rhs.durationSecs)||((this.durationSecs!= null)&&this.durationSecs.equals(rhs.durationSecs))))&&((this.displayname == rhs.displayname)||((this.displayname!= null)&&this.displayname.equals(rhs.displayname))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.width == rhs.width)||((this.width!= null)&&this.width.equals(rhs.width))))&&((this.fullname == rhs.fullname)||((this.fullname!= null)&&this.fullname.equals(rhs.fullname))))&&((this.height == rhs.height)||((this.height!= null)&&this.height.equals(rhs.height))))&&((this.md5 == rhs.md5)||((this.md5 != null)&&this.md5 .equals(rhs.md5))));
    }

}
