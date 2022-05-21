package com.x264.webmtotelegram.videoutils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;

public final class Downloader {
    private Downloader() {}

    public static final String PATH_SAVE = System.getProperty("user.dir") + File.separator;

    //Return path to downloaded file
    public static Optional<File> downloadFile(String urlString) {
        Path fileName = Path.of(PATH_SAVE + FilenameUtils.getName(urlString));
        File file = fileName.toFile();
        try {
            URL url = new URL(urlString);
            FileUtils.copyURLToFile(url, file);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(file);
    }
}
