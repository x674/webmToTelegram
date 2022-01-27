package com.x264.webmtotelegram.ImageBoard;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);

    public String ConvertWebmToMP4(String URLVideo) {
        URL urlVideo = null;
        try {
            urlVideo = new URL(URLVideo);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(128000);
        VideoAttributes video = new VideoAttributes();
        video.setCodec("libx264");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        Encoder encoder = new Encoder();

        //Out file
        File target = null;
        try {
            target = Files.createFile(Path.of(System.getProperty("java.io.tmpdir")+URLVideo.substring(URLVideo.lastIndexOf("/")+1)+".mp4")).toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            encoder.encode(new MultimediaObject(urlVideo), target, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        log.info("Converted to"+ target.getAbsolutePath());
        return target.getAbsolutePath();
    }
}
