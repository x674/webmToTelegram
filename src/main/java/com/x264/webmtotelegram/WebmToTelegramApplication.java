package com.x264.webmtotelegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebmToTelegramApplication {
    private static final Logger log = LoggerFactory.getLogger(WebmToTelegramApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebmToTelegramApplication.class, args);
    }
}
