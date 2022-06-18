package com.x264.webmtotelegram.imageboard.fourchan.rest;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ThreadPages {

    @JsonProperty("page")
    private Integer page;
    @JsonProperty("threads")
    private List<Thread> threads = new ArrayList<Thread>();

    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    @JsonProperty("page")
    public void setPage(Integer page) {
        this.page = page;
    }

    @JsonProperty("threads")
    public List<Thread> getThreads() {
        return threads;
    }

    @JsonProperty("threads")
    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

}
