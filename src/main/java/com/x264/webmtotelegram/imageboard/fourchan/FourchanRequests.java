package com.x264.webmtotelegram.imageboard.fourchan;

import com.x264.webmtotelegram.imageboard.fourchan.rest.ThreadPages;
import com.x264.webmtotelegram.imageboard.fourchan.rest.ThreadPosts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.constraints.NotNull;

@Component
public class FourchanRequests {
    public static final String HOSTNAME = "https://a.4cdn.org/";
    private static final Logger log = LoggerFactory.getLogger(FourchanRequests.class);
    private final WebClient webClient;

    private FourchanRequests() {
        webClient = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build()).baseUrl(HOSTNAME).build();
    }

    public ThreadPages getThreadPages(@NotNull String board) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{board}/threads.json").build(board))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    log.error("Error while getting catalog: {}", clientResponse.statusCode());
                    return null;
                })
                .bodyToMono(ThreadPages.class).block();
    }

    public ThreadPosts getThreadPosts(@NotNull String board, @NotNull String numThread) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{board}/thread/{idThread}.json").build(board, numThread))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    log.error("Error while getting thread posts: {}", clientResponse.statusCode());
                    return null;
                })
                .bodyToMono(ThreadPosts.class).block();
    }

}
