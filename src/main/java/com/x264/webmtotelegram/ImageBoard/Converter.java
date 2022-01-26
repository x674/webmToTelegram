package com.x264.webmtotelegram.ImageBoard;

import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    public FileSystem fileSystem;

    public Converter() {
        this.fileSystem = Jimfs.newFileSystem(Configuration.forCurrentPlatform());
    }

    public String ConvertWebmToMP4(String URLVideo) {
        URL urlFile = null;
        try {
            urlFile = new URI(URLVideo).toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
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

        //Out file in memory FileSystem
        Path path = fileSystem.getPath("").resolve(urlFile.getPath().substring(urlFile.getPath().lastIndexOf("/")+1)+".mp4");
        try {
            Files.write(path, ImmutableList.of("hello world"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File target = new File(path.toUri());
        try {
            encoder.encode(new MultimediaObject(urlFile), target, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        log.info("Converted to"+ target.getAbsolutePath());
        return target.getAbsolutePath();
    }
}
