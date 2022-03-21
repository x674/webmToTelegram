package com.x264.webmtotelegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@SpringBootApplication
public class WebmToTelegramApplication {
    private static final Logger log = LoggerFactory.getLogger(WebmToTelegramApplication.class);

    public static void main(String[] args) {
        CheckMyIp();
        SpringApplication.run(WebmToTelegramApplication.class, args);
    }
    private static void CheckMyIp(){

        BufferedReader br = null;
        try {
            URL url = new URL("http://checkip.amazonaws.com/");
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            System.out.println(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
