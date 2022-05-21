package com.x264.webmtotelegram.videoutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private Encoder encoder;
    String VIDEO_ENCODER = "libx264";
    String AUDIO_ENCODER = "aac";
    List<String> notSupportedCodec = Arrays.asList("isom");

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
    public boolean CheckFileCodecs(String url){
        URL urlObj = null;
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        MultimediaObject multimediaObject = new MultimediaObject(urlObj);
        try {
            var info = multimediaObject.getInfo();
            var major_brand = info.getMetadata().get("major_brand");
            return notSupportedCodec.stream().anyMatch(codec -> Pattern.compile(codec, Pattern.CASE_INSENSITIVE).matcher(major_brand).find());
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }
    }

    public MultimediaInfo GetMultimediaInfo(String url){
        URL urlObj = null;
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        MultimediaObject multimediaObject = new MultimediaObject(urlObj);
        try {
            var info = multimediaObject.getInfo();
            return info;
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<File> convertWebmToMP4(String URLVideo) {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec(AUDIO_ENCODER);
        VideoAttributes video = new VideoAttributes();
        video.setCodec(VIDEO_ENCODER);
        video.setCodec(VIDEO_ENCODER);
        var multimediaInfo = GetMultimediaInfo(URLVideo);
        Integer bitrate = Math.toIntExact(50 * 8192 / TimeUnit.MILLISECONDS.toSeconds(multimediaInfo.getDuration()))*100;
        video.setBitRate(bitrate);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        HashMap<String,String> extraParams = new HashMap<>();
        extraParams.put("-brand","mp42");
        attrs.setExtraContext(extraParams);
        // Out file
        Path filePath = Paths.get(System.getProperty("user.dir"),URLVideo.substring(URLVideo.lastIndexOf("/") + 1) + ".mp4");// Path.of(PATH_SAVE + URLVideo.substring(URLVideo.lastIndexOf("/") + 1) + ".mp4");

        if (Files.exists(filePath)) {
            return Optional.of(filePath.toFile());
        }

        try {
            Path path = Files.createFile(filePath);
            File target = path.toFile();
            URL urlVideo = new URL(URLVideo);
            log.info("Start convert {}", urlVideo);
            encoder.encode(new MultimediaObject(urlVideo), target, attrs);
            log.info("Converted to {}", target.getPath());
            return Optional.of(filePath.toFile());
        } catch (EncoderException | IOException e){
            e.printStackTrace();
            log.error(URLVideo, attrs);
            log.error(attrs.toString());
        }
        return Optional.empty();
    }
}
