package com.x264.webmtotelegram;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dvach")
public class DvachProperties {

    private Map<String,Board> boards;

    
    @PostConstruct
    public void Test()
    {
        System.out.println("test");
    }

    // /**
    //  * @param dvach the dvach to set
    //  */

    public static class Board
    {
        private List<String> filterWords;

        /**
     * @return List<String> return the filterWords
     */
    public List<String> getFilterWords() {
        return filterWords;
    }

    /**
     * @param filterWords the filterWords to set
     */
    public void setFilterWords(List<String> filterWords) {
        this.filterWords = filterWords;
    }
    }

    /**
     * @return Map<String,Board> return the boards
     */
    public Map<String,Board> getBoards() {
        return boards;
    }

    /**
     * @param boards the boards to set
     */
    public void setBoards(Map<String,Board> boards) {
        this.boards = boards;
    }




}
