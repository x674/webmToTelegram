package com.x264.webmtotelegram.Telegram.Entities;

import java.util.List;

public class TelegramPost {
    private List<String> URLVideos;
    private String ThreadName;
    private String MessageURL;
    
    TelegramPost(List<String> URLVideos,
    String ThreadName,
    String MessageURL)
    {
        this.URLVideos = URLVideos;
        this.ThreadName = ThreadName;
        this.MessageURL = MessageURL;

    }

    /**
     * @return String return the ThreadName
     */
    public String getThreadName() {
        return ThreadName;
    }

    /**
     * @param ThreadName the ThreadName to set
     */
    public void setThreadName(String ThreadName) {
        this.ThreadName = ThreadName;
    }

    /**
     * @return String return the MessageURL
     */
    public String getMessageURL() {
        return MessageURL;
    }

    /**
     * @param MessageURL the MessageURL to set
     */
    public void setMessageURL(String MessageURL) {
        this.MessageURL = MessageURL;
    }


    /**
     * @return List<String> return the URLVideos
     */
    public List<String> getURLVideos() {
        return URLVideos;
    }

    /**
     * @param URLVideos the URLVideos to set
     */
    public void setURLVideos(List<String> URLVideos) {
        this.URLVideos = URLVideos;
    }

}
