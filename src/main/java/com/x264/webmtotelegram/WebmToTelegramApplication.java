package com.x264.webmtotelegram;

import com.x264.webmtotelegram.ImageBoard.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebmToTelegramApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(WebmToTelegramApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(WebmToTelegramApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        //var path =new Converter().ConvertWebmToMP4("https://2ch.hk/b/src/262044424/16431863491570.webm");
        //log.info("CONVERTED TO "+ path);
        log.info("Start");
    }
}
