package com.x264.webmtotelegram.videoutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private Encoder encoder;
    String VIDEO_ENCODER = "libx265";
    String AUDIO_ENCODER = "aac";

    public Converter() {
        encoder = new Encoder();
        String[] encoders;
        // If availible Nvidia GPU
//        try {
//            encoders = encoder.getVideoEncoders();
//            if (encoders != null)
//                if (Arrays.stream(encoders).anyMatch(e -> e.contains("h264_nvenc")))
//                    VIDEO_ENCODER = "h264_nvenc";
//        } catch (EncoderException e) {
//            e.printStackTrace();
//        }
    }

    public Boolean CheckFileCodecs(String url) throws MalformedURLException, EncoderException {
        URL urlObj = null;
        urlObj = new URL(url);
        MultimediaObject multimediaObject = new MultimediaObject(urlObj);
        var info = multimediaObject.getInfo();
        var major_brand = info.getMetadata().get("major_brand");
        return major_brand.contains("mp42");
    }

    public MultimediaInfo GetMultimediaInfo(String url)
            throws  EncoderException, MalformedURLException {
        URL urlObj = null;
        urlObj = new URL(url);
        MultimediaObject multimediaObject = new MultimediaObject(urlObj);
        var info = multimediaObject.getInfo();
        return info;
    }

    public File convertWebmToMP4(String URLVideo) throws IOException, IllegalArgumentException, EncoderException {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec(AUDIO_ENCODER);
        VideoAttributes video = new VideoAttributes();
        video.setCodec(VIDEO_ENCODER);
        video.setCodec(VIDEO_ENCODER);
        var multimediaInfo = GetMultimediaInfo(URLVideo);

        long duration = multimediaInfo.getDuration();
//        if (duration >= 5000) {
//            Integer bitrate = Math.toIntExact((50 * 8192) / TimeUnit.MILLISECONDS.toSeconds(duration)) * 100;
//            video.setBitRate(bitrate);
//        }
        video.setQuality(24);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        HashMap<String, String> extraParams = new HashMap<>();
        extraParams.put("-brand", "mp42");
        attrs.setExtraContext(extraParams);
        // Out file
        Path filePath = Paths.get(System.getProperty("user.dir"),
                URLVideo.substring(URLVideo.lastIndexOf("/") + 1) + ".mp4");

        if (Files.exists(filePath)) {
            return filePath.toFile();
        }

        Path path = Files.createFile(filePath);
        File target = path.toFile();
        URL urlVideo = new URL(URLVideo);
        log.info("Start convert {}", urlVideo);
        encoder.encode(new MultimediaObject(urlVideo), target, attrs);
        log.info("Converted to {}", target.getPath());
        return filePath.toFile();
        
    }
}
