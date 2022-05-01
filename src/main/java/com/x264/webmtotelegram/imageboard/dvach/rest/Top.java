package com.x264.webmtotelegram.imageboard.dvach.rest;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "board",
    "info",
    "name"
})
@Generated("jsonschema2pojo")
public class Top {

    @JsonProperty("board")
    private String board;
    @JsonProperty("info")
    private String info;
    @JsonProperty("name")
    private String name;

    @JsonProperty("board")
    public String getBoard() {
        return board;
    }

    @JsonProperty("board")
    public void setBoard(String board) {
        this.board = board;
    }

    @JsonProperty("info")
    public String getInfo() {
        return info;
    }

    @JsonProperty("info")
    public void setInfo(String info) {
        this.info = info;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Top.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("board");
        sb.append('=');
        sb.append(((this.board == null)?"<null>":this.board));
        sb.append(',');
        sb.append("info");
        sb.append('=');
        sb.append(((this.info == null)?"<null>":this.info));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
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
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.board == null)? 0 :this.board.hashCode()));
        result = ((result* 31)+((this.info == null)? 0 :this.info.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Top) == false) {
            return false;
        }
        Top rhs = ((Top) other);
        return ((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.board == rhs.board)||((this.board!= null)&&this.board.equals(rhs.board))))&&((this.info == rhs.info)||((this.info!= null)&&this.info.equals(rhs.info))));
    }

}
