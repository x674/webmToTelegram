package com.x264.webmtotelegram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dvach")
public class DvachProperties {

    private Map<String, Board> boards;

    public static class Board {
        private List<String> filterWords = new ArrayList<>();
        private List<String> ignoreWords = new ArrayList<>();;

        public List<String> getIgnoreWords() {
            return ignoreWords;
        }

        public void setIgnoreWords(List<String> ignoreWords) {
            this.ignoreWords = ignoreWords;
        }

        public List<String> getFilterWords() {
            return filterWords;
        }

        public void setFilterWords(List<String> filterWords) {
            this.filterWords = filterWords;
        }
    }

    public Map<String, Board> getBoards() {
        return boards;
    }

    public void setBoards(Map<String, Board> boards) {
        this.boards = boards;
    }


}
