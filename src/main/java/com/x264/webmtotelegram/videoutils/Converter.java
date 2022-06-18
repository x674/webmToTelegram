package com.x264.webmtotelegram.videoutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.filters.Filter;
import ws.schild.jave.filters.PadFilter;
import ws.schild.jave.filters.ScaleFilter;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private Encoder encoder = new Encoder();
    public Boolean CheckFileCodecs(String url) throws MalformedURLException, EncoderException {
        URL urlObj = new URL(url);
        MultimediaObject multimediaObject = new MultimediaObject(urlObj);
        var info = multimediaObject.getInfo();
        var major_brand = info.getMetadata().get("major_brand");
        return major_brand.contains("mp42");
    }

    public MultimediaInfo GetMultimediaInfo(String url)
            throws EncoderException, MalformedURLException {
        URL urlObj = new URL(url);
        MultimediaObject multimediaObject = new MultimediaObject(urlObj);
        var info = multimediaObject.getInfo();
        return info;
    }

    public File convertWebmToMP4(String URLVideo) throws IOException, IllegalArgumentException, EncoderException {

        VideoAttributes videoAttributes = new VideoAttributes();
        Filter filter = new Filter("pad");
        filter.addNamedArgument("w", "ceil(iw/2)*2");
        filter.addNamedArgument("h", "ceil(ih/2)*2");
        videoAttributes.addFilter(filter);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setAudioAttributes(new AudioAttributes());
        attrs.setVideoAttributes(videoAttributes);
        HashMap<String, String> extraParams = new HashMap<>();
        extraParams.put("-brand", "mp42");
        attrs.setExtraContext(extraParams);

        Path filePath = Paths.get(System.getProperty("user.dir"),
                URLVideo.substring(URLVideo.lastIndexOf("/") + 1) + ".mp4");

        if (Files.exists(filePath)) {
            Files.delete(filePath);
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
