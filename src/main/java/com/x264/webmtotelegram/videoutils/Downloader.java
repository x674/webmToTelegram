package com.x264.webmtotelegram.videoutils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

public class Downloader {
    private Downloader() {
    }

    public static final String PATH_SAVE = System.getProperty("user.dir") + File.separator;

    //Return path to downloaded file
    public static File downloadFile(String URLVideo) {
        Path fileName = Path.of(PATH_SAVE + FilenameUtils.getName(URLVideo));
        File file = fileName.toFile();
        URL url;
        try {
            url = URI.create(URLVideo).toURL();
            FileUtils.copyURLToFile(url, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
