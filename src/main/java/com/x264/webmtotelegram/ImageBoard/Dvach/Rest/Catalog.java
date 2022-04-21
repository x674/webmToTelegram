package com.x264.webmtotelegram.ImageBoard.Dvach.Rest;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Board",
    "BoardInfo",
    "BoardInfoOuter",
    "BoardName",
    "advert_bottom_image",
    "advert_bottom_link",
    "advert_mobile_image",
    "advert_mobile_link",
    "advert_top_image",
    "advert_top_link",
    "board_banner_image",
    "board_banner_link",
    "bump_limit",
    "default_name",
    "enable_dices",
    "enable_flags",
    "enable_icons",
    "enable_images",
    "enable_likes",
    "enable_names",
    "enable_oekaki",
    "enable_posting",
    "enable_sage",
    "enable_shield",
    "enable_subject",
    "enable_thread_tags",
    "enable_trips",
    "enable_video",
    "filter",
    "max_comment",
    "max_files_size",
    "news_abu",
    "threads",
    "top"
})
@Generated("jsonschema2pojo")
public class Catalog {

    @JsonProperty("Board")
    private String board;
    @JsonProperty("BoardInfo")
    private String boardInfo;
    @JsonProperty("BoardInfoOuter")
    private String boardInfoOuter;
    @JsonProperty("BoardName")
    private String boardName;
    @JsonProperty("bump_limit")
    private Integer bumpLimit;
    @JsonProperty("default_name")
    private String defaultName;
    @JsonProperty("enable_dices")
    private Integer enableDices;
    @JsonProperty("enable_flags")
    private Integer enableFlags;
    @JsonProperty("enable_icons")
    private Integer enableIcons;
    @JsonProperty("enable_images")
    private Integer enableImages;
    @JsonProperty("enable_likes")
    private Integer enableLikes;
    @JsonProperty("enable_names")
    private Integer enableNames;
    @JsonProperty("enable_oekaki")
    private Integer enableOekaki;
    @JsonProperty("enable_posting")
    private Integer enablePosting;
    @JsonProperty("enable_sage")
    private Integer enableSage;
    @JsonProperty("enable_shield")
    private Integer enableShield;
    @JsonProperty("enable_subject")
    private Integer enableSubject;
    @JsonProperty("enable_thread_tags")
    private Integer enableThreadTags;
    @JsonProperty("enable_trips")
    private Integer enableTrips;
    @JsonProperty("enable_video")
    private Integer enableVideo;
    @JsonProperty("filter")
    private String filter;
    @JsonProperty("max_comment")
    private Integer maxComment;
    @JsonProperty("max_files_size")
    private Integer maxFilesSize;
    @JsonProperty("threads")
    private List<Thread> threads = new ArrayList<Thread>();
    @JsonProperty("top")
    private List<Top> top = new ArrayList<Top>();

    @JsonProperty("Board")
    public String getBoard() {
        return board;
    }

    @JsonProperty("Board")
    public void setBoard(String board) {
        this.board = board;
    }

    @JsonProperty("BoardInfo")
    public String getBoardInfo() {
        return boardInfo;
    }

    @JsonProperty("BoardInfo")
    public void setBoardInfo(String boardInfo) {
        this.boardInfo = boardInfo;
    }

    @JsonProperty("BoardInfoOuter")
    public String getBoardInfoOuter() {
        return boardInfoOuter;
    }

    @JsonProperty("BoardInfoOuter")
    public void setBoardInfoOuter(String boardInfoOuter) {
        this.boardInfoOuter = boardInfoOuter;
    }

    @JsonProperty("BoardName")
    public String getBoardName() {
        return boardName;
    }

    @JsonProperty("BoardName")
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @JsonProperty("bump_limit")
    public Integer getBumpLimit() {
        return bumpLimit;
    }

    @JsonProperty("bump_limit")
    public void setBumpLimit(Integer bumpLimit) {
        this.bumpLimit = bumpLimit;
    }

    @JsonProperty("default_name")
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty("default_name")
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    @JsonProperty("enable_dices")
    public Integer getEnableDices() {
        return enableDices;
    }

    @JsonProperty("enable_dices")
    public void setEnableDices(Integer enableDices) {
        this.enableDices = enableDices;
    }

    @JsonProperty("enable_flags")
    public Integer getEnableFlags() {
        return enableFlags;
    }

    @JsonProperty("enable_flags")
    public void setEnableFlags(Integer enableFlags) {
        this.enableFlags = enableFlags;
    }

    @JsonProperty("enable_icons")
    public Integer getEnableIcons() {
        return enableIcons;
    }

    @JsonProperty("enable_icons")
    public void setEnableIcons(Integer enableIcons) {
        this.enableIcons = enableIcons;
    }

    @JsonProperty("enable_images")
    public Integer getEnableImages() {
        return enableImages;
    }

    @JsonProperty("enable_images")
    public void setEnableImages(Integer enableImages) {
        this.enableImages = enableImages;
    }

    @JsonProperty("enable_likes")
    public Integer getEnableLikes() {
        return enableLikes;
    }

    @JsonProperty("enable_likes")
    public void setEnableLikes(Integer enableLikes) {
        this.enableLikes = enableLikes;
    }

    @JsonProperty("enable_names")
    public Integer getEnableNames() {
        return enableNames;
    }

    @JsonProperty("enable_names")
    public void setEnableNames(Integer enableNames) {
        this.enableNames = enableNames;
    }

    @JsonProperty("enable_oekaki")
    public Integer getEnableOekaki() {
        return enableOekaki;
    }

    @JsonProperty("enable_oekaki")
    public void setEnableOekaki(Integer enableOekaki) {
        this.enableOekaki = enableOekaki;
    }

    @JsonProperty("enable_posting")
    public Integer getEnablePosting() {
        return enablePosting;
    }

    @JsonProperty("enable_posting")
    public void setEnablePosting(Integer enablePosting) {
        this.enablePosting = enablePosting;
    }

    @JsonProperty("enable_sage")
    public Integer getEnableSage() {
        return enableSage;
    }

    @JsonProperty("enable_sage")
    public void setEnableSage(Integer enableSage) {
        this.enableSage = enableSage;
    }

    @JsonProperty("enable_shield")
    public Integer getEnableShield() {
        return enableShield;
    }

    @JsonProperty("enable_shield")
    public void setEnableShield(Integer enableShield) {
        this.enableShield = enableShield;
    }

    @JsonProperty("enable_subject")
    public Integer getEnableSubject() {
        return enableSubject;
    }

    @JsonProperty("enable_subject")
    public void setEnableSubject(Integer enableSubject) {
        this.enableSubject = enableSubject;
    }

    @JsonProperty("enable_thread_tags")
    public Integer getEnableThreadTags() {
        return enableThreadTags;
    }

    @JsonProperty("enable_thread_tags")
    public void setEnableThreadTags(Integer enableThreadTags) {
        this.enableThreadTags = enableThreadTags;
    }

    @JsonProperty("enable_trips")
    public Integer getEnableTrips() {
        return enableTrips;
    }

    @JsonProperty("enable_trips")
    public void setEnableTrips(Integer enableTrips) {
        this.enableTrips = enableTrips;
    }

    @JsonProperty("enable_video")
    public Integer getEnableVideo() {
        return enableVideo;
    }

    @JsonProperty("enable_video")
    public void setEnableVideo(Integer enableVideo) {
        this.enableVideo = enableVideo;
    }

    @JsonProperty("filter")
    public String getFilter() {
        return filter;
    }

    @JsonProperty("filter")
    public void setFilter(String filter) {
        this.filter = filter;
    }

    @JsonProperty("max_comment")
    public Integer getMaxComment() {
        return maxComment;
    }

    @JsonProperty("max_comment")
    public void setMaxComment(Integer maxComment) {
        this.maxComment = maxComment;
    }

    @JsonProperty("max_files_size")
    public Integer getMaxFilesSize() {
        return maxFilesSize;
    }

    @JsonProperty("max_files_size")
    public void setMaxFilesSize(Integer maxFilesSize) {
        this.maxFilesSize = maxFilesSize;
    }

    @JsonProperty("threads")
    public List<Thread> getThreads() {
        return threads;
    }

    @JsonProperty("threads")
    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    @JsonProperty("top")
    public List<Top> getTop() {
        return top;
    }

    @JsonProperty("top")
    public void setTop(List<Top> top) {
        this.top = top;
    }

}
