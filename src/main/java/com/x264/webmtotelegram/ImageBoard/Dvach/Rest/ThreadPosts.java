package com.x264.webmtotelegram.ImageBoard.Dvach.Rest;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ThreadPosts {

    @JsonProperty("Board")
    private String board;
    @JsonProperty("BoardInfo")
    private String boardInfo;
    @JsonProperty("BoardInfoOuter")
    private String boardInfoOuter;
    @JsonProperty("BoardName")
    private String boardName;
    @JsonProperty("board_banner_image")
    private String boardBannerImage;
    @JsonProperty("board_banner_link")
    private String boardBannerLink;
    @JsonProperty("bump_limit")
    private Integer bumpLimit;
    @JsonProperty("current_thread")
    private String currentThread;
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
    @JsonProperty("files_count")
    private Integer filesCount;
    @JsonProperty("is_board")
    private Integer isBoard;
    @JsonProperty("is_closed")
    private Integer isClosed;
    @JsonProperty("is_index")
    private Integer isIndex;
    @JsonProperty("max_comment")
    private Integer maxComment;
    @JsonProperty("max_files_size")
    private Integer maxFilesSize;
    @JsonProperty("max_num")
    private Integer maxNum;
    @JsonProperty("news_abu")
    private List<NewsAbu> newsAbu = new ArrayList<NewsAbu>();
    @JsonProperty("posts_count")
    private Integer postsCount;
    @JsonProperty("thread_first_image")
    private String threadFirstImage;
    @JsonProperty("threads")
    private List<Thread> threads = new ArrayList<Thread>();
    @JsonProperty("title")
    private String title;
    @JsonProperty("top")
    private List<Top> top = new ArrayList<Top>();
    @JsonProperty("unique_posters")
    private String uniquePosters;

    public List<Post> getPosts() {
        return getThreads().stream().findFirst().get().getPosts();
    }

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

    @JsonProperty("board_banner_image")
    public String getBoardBannerImage() {
        return boardBannerImage;
    }

    @JsonProperty("board_banner_image")
    public void setBoardBannerImage(String boardBannerImage) {
        this.boardBannerImage = boardBannerImage;
    }

    @JsonProperty("board_banner_link")
    public String getBoardBannerLink() {
        return boardBannerLink;
    }

    @JsonProperty("board_banner_link")
    public void setBoardBannerLink(String boardBannerLink) {
        this.boardBannerLink = boardBannerLink;
    }

    @JsonProperty("bump_limit")
    public Integer getBumpLimit() {
        return bumpLimit;
    }

    @JsonProperty("bump_limit")
    public void setBumpLimit(Integer bumpLimit) {
        this.bumpLimit = bumpLimit;
    }

    @JsonProperty("current_thread")
    public String getCurrentThread() {
        return currentThread;
    }

    @JsonProperty("current_thread")
    public void setCurrentThread(String currentThread) {
        this.currentThread = currentThread;
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

    @JsonProperty("files_count")
    public Integer getFilesCount() {
        return filesCount;
    }

    @JsonProperty("files_count")
    public void setFilesCount(Integer filesCount) {
        this.filesCount = filesCount;
    }

    @JsonProperty("is_board")
    public Integer getIsBoard() {
        return isBoard;
    }

    @JsonProperty("is_board")
    public void setIsBoard(Integer isBoard) {
        this.isBoard = isBoard;
    }

    @JsonProperty("is_closed")
    public Integer getIsClosed() {
        return isClosed;
    }

    @JsonProperty("is_closed")
    public void setIsClosed(Integer isClosed) {
        this.isClosed = isClosed;
    }

    @JsonProperty("is_index")
    public Integer getIsIndex() {
        return isIndex;
    }

    @JsonProperty("is_index")
    public void setIsIndex(Integer isIndex) {
        this.isIndex = isIndex;
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

    @JsonProperty("max_num")
    public Integer getMaxNum() {
        return maxNum;
    }

    @JsonProperty("max_num")
    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    @JsonProperty("news_abu")
    public List<NewsAbu> getNewsAbu() {
        return newsAbu;
    }

    @JsonProperty("news_abu")
    public void setNewsAbu(List<NewsAbu> newsAbu) {
        this.newsAbu = newsAbu;
    }

    @JsonProperty("posts_count")
    public Integer getPostsCount() {
        return postsCount;
    }

    @JsonProperty("posts_count")
    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    @JsonProperty("thread_first_image")
    public String getThreadFirstImage() {
        return threadFirstImage;
    }

    @JsonProperty("thread_first_image")
    public void setThreadFirstImage(String threadFirstImage) {
        this.threadFirstImage = threadFirstImage;
    }

    @JsonProperty("threads")
    public List<Thread> getThreads() {
        return threads;
    }

    @JsonProperty("threads")
    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("top")
    public List<Top> getTop() {
        return top;
    }

    @JsonProperty("top")
    public void setTop(List<Top> top) {
        this.top = top;
    }

    @JsonProperty("unique_posters")
    public String getUniquePosters() {
        return uniquePosters;
    }

    @JsonProperty("unique_posters")
    public void setUniquePosters(String uniquePosters) {
        this.uniquePosters = uniquePosters;
    }

}
