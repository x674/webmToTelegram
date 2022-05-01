package com.x264.webmtotelegram.imageboard.dvach.rest;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Thread {

    @JsonProperty("posts")
    private List<Post> posts = new ArrayList<Post>();

    @JsonProperty("posts")
    public List<Post> getPosts() {
        return posts;
    }

    @JsonProperty("posts")
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


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
    @JsonProperty("files_count")
    private Integer filesCount;
    @JsonProperty("lasthit")
    private Integer lasthit;
    @JsonProperty("name")
    private String name;
    @JsonProperty("num")
    private String num;
    @JsonProperty("op")
    private Integer op;
    @JsonProperty("parent")
    private String parent;
    @JsonProperty("posts_count")
    private Integer postsCount;
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

    @JsonProperty("files_count")
    public Integer getFilesCount() {
        return filesCount;
    }

    @JsonProperty("files_count")
    public void setFilesCount(Integer filesCount) {
        this.filesCount = filesCount;
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
    public String getNum() {
        return num;
    }

    @JsonProperty("num")
    public void setNum(String num) {
        this.num = num;
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

    @JsonProperty("posts_count")
    public Integer getPostsCount() {
        return postsCount;
    }

    @JsonProperty("posts_count")
    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Thread.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("banned");
        sb.append('=');
        sb.append(((this.banned == null)?"<null>":this.banned));
        sb.append(',');
        sb.append("closed");
        sb.append('=');
        sb.append(((this.closed == null)?"<null>":this.closed));
        sb.append(',');
        sb.append("comment");
        sb.append('=');
        sb.append(((this.comment == null)?"<null>":this.comment));
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("email");
        sb.append('=');
        sb.append(((this.email == null)?"<null>":this.email));
        sb.append(',');
        sb.append("endless");
        sb.append('=');
        sb.append(((this.endless == null)?"<null>":this.endless));
        sb.append(',');
        sb.append("files");
        sb.append('=');
        sb.append(((this.files == null)?"<null>":this.files));
        sb.append(',');
        sb.append("filesCount");
        sb.append('=');
        sb.append(((this.filesCount == null)?"<null>":this.filesCount));
        sb.append(',');
        sb.append("lasthit");
        sb.append('=');
        sb.append(((this.lasthit == null)?"<null>":this.lasthit));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("num");
        sb.append('=');
        sb.append(((this.num == null)?"<null>":this.num));
        sb.append(',');
        sb.append("op");
        sb.append('=');
        sb.append(((this.op == null)?"<null>":this.op));
        sb.append(',');
        sb.append("parent");
        sb.append('=');
        sb.append(((this.parent == null)?"<null>":this.parent));
        sb.append(',');
        sb.append("postsCount");
        sb.append('=');
        sb.append(((this.postsCount == null)?"<null>":this.postsCount));
        sb.append(',');
        sb.append("sticky");
        sb.append('=');
        sb.append(((this.sticky == null)?"<null>":this.sticky));
        sb.append(',');
        sb.append("subject");
        sb.append('=');
        sb.append(((this.subject == null)?"<null>":this.subject));
        sb.append(',');
        sb.append("tags");
        sb.append('=');
        sb.append(((this.tags == null)?"<null>":this.tags));
        sb.append(',');
        sb.append("timestamp");
        sb.append('=');
        sb.append(((this.timestamp == null)?"<null>":this.timestamp));
        sb.append(',');
        sb.append("trip");
        sb.append('=');
        sb.append(((this.trip == null)?"<null>":this.trip));
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
        result = ((result* 31)+((this.date == null)? 0 :this.date.hashCode()));
        result = ((result* 31)+((this.op == null)? 0 :this.op.hashCode()));
        result = ((result* 31)+((this.parent == null)? 0 :this.parent.hashCode()));
        result = ((result* 31)+((this.subject == null)? 0 :this.subject.hashCode()));
        result = ((result* 31)+((this.num == null)? 0 :this.num.hashCode()));
        result = ((result* 31)+((this.endless == null)? 0 :this.endless.hashCode()));
        result = ((result* 31)+((this.lasthit == null)? 0 :this.lasthit.hashCode()));
        result = ((result* 31)+((this.tags == null)? 0 :this.tags.hashCode()));
        result = ((result* 31)+((this.filesCount == null)? 0 :this.filesCount.hashCode()));
        result = ((result* 31)+((this.trip == null)? 0 :this.trip.hashCode()));
        result = ((result* 31)+((this.postsCount == null)? 0 :this.postsCount.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.sticky == null)? 0 :this.sticky.hashCode()));
        result = ((result* 31)+((this.closed == null)? 0 :this.closed.hashCode()));
        result = ((result* 31)+((this.files == null)? 0 :this.files.hashCode()));
        result = ((result* 31)+((this.comment == null)? 0 :this.comment.hashCode()));
        result = ((result* 31)+((this.banned == null)? 0 :this.banned.hashCode()));
        result = ((result* 31)+((this.email == null)? 0 :this.email.hashCode()));
        result = ((result* 31)+((this.timestamp == null)? 0 :this.timestamp.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Thread) == false) {
            return false;
        }
        Thread rhs = ((Thread) other);
        return ((((((((((((((((((((this.date == rhs.date)||((this.date!= null)&&this.date.equals(rhs.date)))&&((this.op == rhs.op)||((this.op!= null)&&this.op.equals(rhs.op))))&&((this.parent == rhs.parent)||((this.parent!= null)&&this.parent.equals(rhs.parent))))&&((this.subject == rhs.subject)||((this.subject!= null)&&this.subject.equals(rhs.subject))))&&((this.num == rhs.num)||((this.num!= null)&&this.num.equals(rhs.num))))&&((this.endless == rhs.endless)||((this.endless!= null)&&this.endless.equals(rhs.endless))))&&((this.lasthit == rhs.lasthit)||((this.lasthit!= null)&&this.lasthit.equals(rhs.lasthit))))&&((this.tags == rhs.tags)||((this.tags!= null)&&this.tags.equals(rhs.tags))))&&((this.filesCount == rhs.filesCount)||((this.filesCount!= null)&&this.filesCount.equals(rhs.filesCount))))&&((this.trip == rhs.trip)||((this.trip!= null)&&this.trip.equals(rhs.trip))))&&((this.postsCount == rhs.postsCount)||((this.postsCount!= null)&&this.postsCount.equals(rhs.postsCount))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.sticky == rhs.sticky)||((this.sticky!= null)&&this.sticky.equals(rhs.sticky))))&&((this.closed == rhs.closed)||((this.closed!= null)&&this.closed.equals(rhs.closed))))&&((this.files == rhs.files)||((this.files!= null)&&this.files.equals(rhs.files))))&&((this.comment == rhs.comment)||((this.comment!= null)&&this.comment.equals(rhs.comment))))&&((this.banned == rhs.banned)||((this.banned!= null)&&this.banned.equals(rhs.banned))))&&((this.email == rhs.email)||((this.email!= null)&&this.email.equals(rhs.email))))&&((this.timestamp == rhs.timestamp)||((this.timestamp!= null)&&this.timestamp.equals(rhs.timestamp))));
    }

}
