package com.x264.webmtotelegram.imageboard.dvach;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import com.x264.webmtotelegram.imageboard.dvach.rest.Catalog;
import com.x264.webmtotelegram.imageboard.dvach.rest.Thread;
import com.x264.webmtotelegram.imageboard.dvach.rest.ThreadPosts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Requests {
    public static final String HOSTNAME = "https://2ch.hk";
    private static final Logger log = LoggerFactory.getLogger(Requests.class);
    private final WebClient webClient;

    private List<String> filterWords = Arrays.asList("WEBM");

    private Predicate<Thread> threadFilter = thread -> {
        if (!filterWords.isEmpty()) {
            for (var word : filterWords) {
                if (Pattern.compile(Pattern.quote(word), Pattern.CASE_INSENSITIVE)
                        .matcher(thread.getSubject()).find())
                    return true;
            }
        } else {
            return true;
        }
        return false;
    };

    private Requests() {
        webClient = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build()).baseUrl(HOSTNAME).build();
    }

    public Catalog getCatalog(@NotNull String board) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{board}/catalog.json").build(board))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException)
                .bodyToMono(Catalog.class).block();
    }

    public ThreadPosts getThreadPosts(@NotNull String board, @NotNull String numThread) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{board}/res/{idThread}.json").build(board, numThread))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException)
                .bodyToMono(ThreadPosts.class).block();
    }
    
}
