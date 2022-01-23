package com.x264.webmtotelegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebmToTelegramApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebmToTelegramApplication.class, args);

    }

}
