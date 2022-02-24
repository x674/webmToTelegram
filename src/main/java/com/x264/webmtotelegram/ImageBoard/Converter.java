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
import java.util.Arrays;

@Component
public class Converter {
    public static final String PATH_SAVE = "D://";
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private Encoder encoder;
    String VIDEO_ENCODER = "libx264";
    String AUDIO_ENCODER = "libmp3lame";
    public Converter() {
        encoder = new Encoder();
        String[] encoders = null;
        try {
            encoders = encoder.getVideoEncoders();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        String[] encodersAudio = null;
        try {
            encodersAudio = encoder.getAudioEncoders();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        //If availible Nvidia GPU
        if (Arrays.stream(encoders).anyMatch(e->e.contains("h264_nvenc")))
            VIDEO_ENCODER = "h264_nvenc";
        //
        if (Arrays.stream(encodersAudio).anyMatch(e->e.contains("aac")))
            AUDIO_ENCODER = "aac";

    }

    public String ConvertWebmToMP4(String URLVideo) {
        URL urlVideo = null;
        try {
            urlVideo = new URL(URLVideo);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec(AUDIO_ENCODER);
        VideoAttributes video = new VideoAttributes();
        video.setCodec(VIDEO_ENCODER);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);

        //Out file
        File target = null;
        var fileName = Path.of(PATH_SAVE + URLVideo.substring(URLVideo.lastIndexOf("/") + 1) + ".mp4");

        try {
            if (Files.notExists(fileName)) {
                target = Files.createFile(fileName).toFile();
                try {
                    encoder.encode(new MultimediaObject(urlVideo), target, attrs);
                } catch (EncoderException e) {
                    e.printStackTrace();
                }
            } else {
                target = fileName.toFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Converted to " + target.getPath());
        return target.getPath();
    }
}
