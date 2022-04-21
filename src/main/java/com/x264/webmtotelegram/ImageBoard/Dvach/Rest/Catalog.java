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
    @JsonProperty("advert_bottom_image")
    private String advertBottomImage;
    @JsonProperty("advert_bottom_link")
    private String advertBottomLink;
    @JsonProperty("advert_mobile_image")
    private String advertMobileImage;
    @JsonProperty("advert_mobile_link")
    private String advertMobileLink;
    @JsonProperty("advert_top_image")
    private String advertTopImage;
    @JsonProperty("advert_top_link")
    private String advertTopLink;
    @JsonProperty("board_banner_image")
    private String boardBannerImage;
    @JsonProperty("board_banner_link")
    private String boardBannerLink;
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
    @JsonProperty("news_abu")
    private List<NewsAbu> newsAbu = new ArrayList<NewsAbu>();
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

    @JsonProperty("advert_bottom_image")
    public String getAdvertBottomImage() {
        return advertBottomImage;
    }

    @JsonProperty("advert_bottom_image")
    public void setAdvertBottomImage(String advertBottomImage) {
        this.advertBottomImage = advertBottomImage;
    }

    @JsonProperty("advert_bottom_link")
    public String getAdvertBottomLink() {
        return advertBottomLink;
    }

    @JsonProperty("advert_bottom_link")
    public void setAdvertBottomLink(String advertBottomLink) {
        this.advertBottomLink = advertBottomLink;
    }

    @JsonProperty("advert_mobile_image")
    public String getAdvertMobileImage() {
        return advertMobileImage;
    }

    @JsonProperty("advert_mobile_image")
    public void setAdvertMobileImage(String advertMobileImage) {
        this.advertMobileImage = advertMobileImage;
    }

    @JsonProperty("advert_mobile_link")
    public String getAdvertMobileLink() {
        return advertMobileLink;
    }

    @JsonProperty("advert_mobile_link")
    public void setAdvertMobileLink(String advertMobileLink) {
        this.advertMobileLink = advertMobileLink;
    }

    @JsonProperty("advert_top_image")
    public String getAdvertTopImage() {
        return advertTopImage;
    }

    @JsonProperty("advert_top_image")
    public void setAdvertTopImage(String advertTopImage) {
        this.advertTopImage = advertTopImage;
    }

    @JsonProperty("advert_top_link")
    public String getAdvertTopLink() {
        return advertTopLink;
    }

    @JsonProperty("advert_top_link")
    public void setAdvertTopLink(String advertTopLink) {
        this.advertTopLink = advertTopLink;
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

    @JsonProperty("news_abu")
    public List<NewsAbu> getNewsAbu() {
        return newsAbu;
    }

    @JsonProperty("news_abu")
    public void setNewsAbu(List<NewsAbu> newsAbu) {
        this.newsAbu = newsAbu;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Catalog.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("board");
        sb.append('=');
        sb.append(((this.board == null)?"<null>":this.board));
        sb.append(',');
        sb.append("boardInfo");
        sb.append('=');
        sb.append(((this.boardInfo == null)?"<null>":this.boardInfo));
        sb.append(',');
        sb.append("boardInfoOuter");
        sb.append('=');
        sb.append(((this.boardInfoOuter == null)?"<null>":this.boardInfoOuter));
        sb.append(',');
        sb.append("boardName");
        sb.append('=');
        sb.append(((this.boardName == null)?"<null>":this.boardName));
        sb.append(',');
        sb.append("advertBottomImage");
        sb.append('=');
        sb.append(((this.advertBottomImage == null)?"<null>":this.advertBottomImage));
        sb.append(',');
        sb.append("advertBottomLink");
        sb.append('=');
        sb.append(((this.advertBottomLink == null)?"<null>":this.advertBottomLink));
        sb.append(',');
        sb.append("advertMobileImage");
        sb.append('=');
        sb.append(((this.advertMobileImage == null)?"<null>":this.advertMobileImage));
        sb.append(',');
        sb.append("advertMobileLink");
        sb.append('=');
        sb.append(((this.advertMobileLink == null)?"<null>":this.advertMobileLink));
        sb.append(',');
        sb.append("advertTopImage");
        sb.append('=');
        sb.append(((this.advertTopImage == null)?"<null>":this.advertTopImage));
        sb.append(',');
        sb.append("advertTopLink");
        sb.append('=');
        sb.append(((this.advertTopLink == null)?"<null>":this.advertTopLink));
        sb.append(',');
        sb.append("boardBannerImage");
        sb.append('=');
        sb.append(((this.boardBannerImage == null)?"<null>":this.boardBannerImage));
        sb.append(',');
        sb.append("boardBannerLink");
        sb.append('=');
        sb.append(((this.boardBannerLink == null)?"<null>":this.boardBannerLink));
        sb.append(',');
        sb.append("bumpLimit");
        sb.append('=');
        sb.append(((this.bumpLimit == null)?"<null>":this.bumpLimit));
        sb.append(',');
        sb.append("defaultName");
        sb.append('=');
        sb.append(((this.defaultName == null)?"<null>":this.defaultName));
        sb.append(',');
        sb.append("enableDices");
        sb.append('=');
        sb.append(((this.enableDices == null)?"<null>":this.enableDices));
        sb.append(',');
        sb.append("enableFlags");
        sb.append('=');
        sb.append(((this.enableFlags == null)?"<null>":this.enableFlags));
        sb.append(',');
        sb.append("enableIcons");
        sb.append('=');
        sb.append(((this.enableIcons == null)?"<null>":this.enableIcons));
        sb.append(',');
        sb.append("enableImages");
        sb.append('=');
        sb.append(((this.enableImages == null)?"<null>":this.enableImages));
        sb.append(',');
        sb.append("enableLikes");
        sb.append('=');
        sb.append(((this.enableLikes == null)?"<null>":this.enableLikes));
        sb.append(',');
        sb.append("enableNames");
        sb.append('=');
        sb.append(((this.enableNames == null)?"<null>":this.enableNames));
        sb.append(',');
        sb.append("enableOekaki");
        sb.append('=');
        sb.append(((this.enableOekaki == null)?"<null>":this.enableOekaki));
        sb.append(',');
        sb.append("enablePosting");
        sb.append('=');
        sb.append(((this.enablePosting == null)?"<null>":this.enablePosting));
        sb.append(',');
        sb.append("enableSage");
        sb.append('=');
        sb.append(((this.enableSage == null)?"<null>":this.enableSage));
        sb.append(',');
        sb.append("enableShield");
        sb.append('=');
        sb.append(((this.enableShield == null)?"<null>":this.enableShield));
        sb.append(',');
        sb.append("enableSubject");
        sb.append('=');
        sb.append(((this.enableSubject == null)?"<null>":this.enableSubject));
        sb.append(',');
        sb.append("enableThreadTags");
        sb.append('=');
        sb.append(((this.enableThreadTags == null)?"<null>":this.enableThreadTags));
        sb.append(',');
        sb.append("enableTrips");
        sb.append('=');
        sb.append(((this.enableTrips == null)?"<null>":this.enableTrips));
        sb.append(',');
        sb.append("enableVideo");
        sb.append('=');
        sb.append(((this.enableVideo == null)?"<null>":this.enableVideo));
        sb.append(',');
        sb.append("filter");
        sb.append('=');
        sb.append(((this.filter == null)?"<null>":this.filter));
        sb.append(',');
        sb.append("maxComment");
        sb.append('=');
        sb.append(((this.maxComment == null)?"<null>":this.maxComment));
        sb.append(',');
        sb.append("maxFilesSize");
        sb.append('=');
        sb.append(((this.maxFilesSize == null)?"<null>":this.maxFilesSize));
        sb.append(',');
        sb.append("newsAbu");
        sb.append('=');
        sb.append(((this.newsAbu == null)?"<null>":this.newsAbu));
        sb.append(',');
        sb.append("threads");
        sb.append('=');
        sb.append(((this.threads == null)?"<null>":this.threads));
        sb.append(',');
        sb.append("top");
        sb.append('=');
        sb.append(((this.top == null)?"<null>":this.top));
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
        result = ((result* 31)+((this.boardBannerImage == null)? 0 :this.boardBannerImage.hashCode()));
        result = ((result* 31)+((this.enableThreadTags == null)? 0 :this.enableThreadTags.hashCode()));
        result = ((result* 31)+((this.maxComment == null)? 0 :this.maxComment.hashCode()));
        result = ((result* 31)+((this.bumpLimit == null)? 0 :this.bumpLimit.hashCode()));
        result = ((result* 31)+((this.advertTopLink == null)? 0 :this.advertTopLink.hashCode()));
        result = ((result* 31)+((this.boardInfo == null)? 0 :this.boardInfo.hashCode()));
        result = ((result* 31)+((this.advertBottomImage == null)? 0 :this.advertBottomImage.hashCode()));
        result = ((result* 31)+((this.enableSubject == null)? 0 :this.enableSubject.hashCode()));
        result = ((result* 31)+((this.enableShield == null)? 0 :this.enableShield.hashCode()));
        result = ((result* 31)+((this.enableIcons == null)? 0 :this.enableIcons.hashCode()));
        result = ((result* 31)+((this.advertMobileImage == null)? 0 :this.advertMobileImage.hashCode()));
        result = ((result* 31)+((this.advertBottomLink == null)? 0 :this.advertBottomLink.hashCode()));
        result = ((result* 31)+((this.enableTrips == null)? 0 :this.enableTrips.hashCode()));
        result = ((result* 31)+((this.maxFilesSize == null)? 0 :this.maxFilesSize.hashCode()));
        result = ((result* 31)+((this.top == null)? 0 :this.top.hashCode()));
        result = ((result* 31)+((this.enablePosting == null)? 0 :this.enablePosting.hashCode()));
        result = ((result* 31)+((this.enableNames == null)? 0 :this.enableNames.hashCode()));
        result = ((result* 31)+((this.enableSage == null)? 0 :this.enableSage.hashCode()));
        result = ((result* 31)+((this.boardBannerLink == null)? 0 :this.boardBannerLink.hashCode()));
        result = ((result* 31)+((this.defaultName == null)? 0 :this.defaultName.hashCode()));
        result = ((result* 31)+((this.advertTopImage == null)? 0 :this.advertTopImage.hashCode()));
        result = ((result* 31)+((this.enableOekaki == null)? 0 :this.enableOekaki.hashCode()));
        result = ((result* 31)+((this.newsAbu == null)? 0 :this.newsAbu.hashCode()));
        result = ((result* 31)+((this.enableVideo == null)? 0 :this.enableVideo.hashCode()));
        result = ((result* 31)+((this.threads == null)? 0 :this.threads.hashCode()));
        result = ((result* 31)+((this.boardInfoOuter == null)? 0 :this.boardInfoOuter.hashCode()));
        result = ((result* 31)+((this.boardName == null)? 0 :this.boardName.hashCode()));
        result = ((result* 31)+((this.enableImages == null)? 0 :this.enableImages.hashCode()));
        result = ((result* 31)+((this.filter == null)? 0 :this.filter.hashCode()));
        result = ((result* 31)+((this.enableFlags == null)? 0 :this.enableFlags.hashCode()));
        result = ((result* 31)+((this.advertMobileLink == null)? 0 :this.advertMobileLink.hashCode()));
        result = ((result* 31)+((this.enableLikes == null)? 0 :this.enableLikes.hashCode()));
        result = ((result* 31)+((this.enableDices == null)? 0 :this.enableDices.hashCode()));
        result = ((result* 31)+((this.board == null)? 0 :this.board.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Catalog) == false) {
            return false;
        }
        Catalog rhs = ((Catalog) other);
        return (((((((((((((((((((((((((((((((((((this.boardBannerImage == rhs.boardBannerImage)||((this.boardBannerImage!= null)&&this.boardBannerImage.equals(rhs.boardBannerImage)))&&((this.enableThreadTags == rhs.enableThreadTags)||((this.enableThreadTags!= null)&&this.enableThreadTags.equals(rhs.enableThreadTags))))&&((this.maxComment == rhs.maxComment)||((this.maxComment!= null)&&this.maxComment.equals(rhs.maxComment))))&&((this.bumpLimit == rhs.bumpLimit)||((this.bumpLimit!= null)&&this.bumpLimit.equals(rhs.bumpLimit))))&&((this.advertTopLink == rhs.advertTopLink)||((this.advertTopLink!= null)&&this.advertTopLink.equals(rhs.advertTopLink))))&&((this.boardInfo == rhs.boardInfo)||((this.boardInfo!= null)&&this.boardInfo.equals(rhs.boardInfo))))&&((this.advertBottomImage == rhs.advertBottomImage)||((this.advertBottomImage!= null)&&this.advertBottomImage.equals(rhs.advertBottomImage))))&&((this.enableSubject == rhs.enableSubject)||((this.enableSubject!= null)&&this.enableSubject.equals(rhs.enableSubject))))&&((this.enableShield == rhs.enableShield)||((this.enableShield!= null)&&this.enableShield.equals(rhs.enableShield))))&&((this.enableIcons == rhs.enableIcons)||((this.enableIcons!= null)&&this.enableIcons.equals(rhs.enableIcons))))&&((this.advertMobileImage == rhs.advertMobileImage)||((this.advertMobileImage!= null)&&this.advertMobileImage.equals(rhs.advertMobileImage))))&&((this.advertBottomLink == rhs.advertBottomLink)||((this.advertBottomLink!= null)&&this.advertBottomLink.equals(rhs.advertBottomLink))))&&((this.enableTrips == rhs.enableTrips)||((this.enableTrips!= null)&&this.enableTrips.equals(rhs.enableTrips))))&&((this.maxFilesSize == rhs.maxFilesSize)||((this.maxFilesSize!= null)&&this.maxFilesSize.equals(rhs.maxFilesSize))))&&((this.top == rhs.top)||((this.top!= null)&&this.top.equals(rhs.top))))&&((this.enablePosting == rhs.enablePosting)||((this.enablePosting!= null)&&this.enablePosting.equals(rhs.enablePosting))))&&((this.enableNames == rhs.enableNames)||((this.enableNames!= null)&&this.enableNames.equals(rhs.enableNames))))&&((this.enableSage == rhs.enableSage)||((this.enableSage!= null)&&this.enableSage.equals(rhs.enableSage))))&&((this.boardBannerLink == rhs.boardBannerLink)||((this.boardBannerLink!= null)&&this.boardBannerLink.equals(rhs.boardBannerLink))))&&((this.defaultName == rhs.defaultName)||((this.defaultName!= null)&&this.defaultName.equals(rhs.defaultName))))&&((this.advertTopImage == rhs.advertTopImage)||((this.advertTopImage!= null)&&this.advertTopImage.equals(rhs.advertTopImage))))&&((this.enableOekaki == rhs.enableOekaki)||((this.enableOekaki!= null)&&this.enableOekaki.equals(rhs.enableOekaki))))&&((this.newsAbu == rhs.newsAbu)||((this.newsAbu!= null)&&this.newsAbu.equals(rhs.newsAbu))))&&((this.enableVideo == rhs.enableVideo)||((this.enableVideo!= null)&&this.enableVideo.equals(rhs.enableVideo))))&&((this.threads == rhs.threads)||((this.threads!= null)&&this.threads.equals(rhs.threads))))&&((this.boardInfoOuter == rhs.boardInfoOuter)||((this.boardInfoOuter!= null)&&this.boardInfoOuter.equals(rhs.boardInfoOuter))))&&((this.boardName == rhs.boardName)||((this.boardName!= null)&&this.boardName.equals(rhs.boardName))))&&((this.enableImages == rhs.enableImages)||((this.enableImages!= null)&&this.enableImages.equals(rhs.enableImages))))&&((this.filter == rhs.filter)||((this.filter!= null)&&this.filter.equals(rhs.filter))))&&((this.enableFlags == rhs.enableFlags)||((this.enableFlags!= null)&&this.enableFlags.equals(rhs.enableFlags))))&&((this.advertMobileLink == rhs.advertMobileLink)||((this.advertMobileLink!= null)&&this.advertMobileLink.equals(rhs.advertMobileLink))))&&((this.enableLikes == rhs.enableLikes)||((this.enableLikes!= null)&&this.enableLikes.equals(rhs.enableLikes))))&&((this.enableDices == rhs.enableDices)||((this.enableDices!= null)&&this.enableDices.equals(rhs.enableDices))))&&((this.board == rhs.board)||((this.board!= null)&&this.board.equals(rhs.board))));
    }

}
