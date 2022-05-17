package com.x264.webmtotelegram.videoutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


public class Converter {
    public static final String PATH_SAVE = System.getProperty("user.dir") + File.separator;
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private Encoder encoder;
    String VIDEO_ENCODER = "libx264";
    String AUDIO_ENCODER = "aac";

    public Converter() {
        encoder = new Encoder();
        String[] encoders;
        // If availible Nvidia GPU
        try {
            encoders = encoder.getVideoEncoders();
            if (encoders != null)
                if (Arrays.stream(encoders).anyMatch(e -> e.contains("h264_nvenc")))
                    VIDEO_ENCODER = "h264_nvenc";
        } catch (EncoderException e) {
            e.printStackTrace();
        }



    }

    public File convertWebmToMP4(String URLVideo) {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec(AUDIO_ENCODER);
        VideoAttributes video = new VideoAttributes();
        video.setCodec(VIDEO_ENCODER);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        // Out file
        var fileName = Path.of(PATH_SAVE + URLVideo.substring(URLVideo.lastIndexOf("/") + 1) + ".mp4");

        if (Files.exists(fileName)) {
            return fileName.toFile();
        }

        try {
            File target = Files.createFile(fileName).toFile();
            URL urlVideo = new URL(URLVideo);
            log.info("Start convert {}", urlVideo);
            encoder.encode(new MultimediaObject(urlVideo), target, attrs);
            log.info("Converted to {}", target.getPath());
            return target;
        } catch (EncoderException | IOException e){
            e.printStackTrace();
            log.error(URLVideo, attrs);
            log.error(attrs.toString());
        }
        return null;
    }
}
