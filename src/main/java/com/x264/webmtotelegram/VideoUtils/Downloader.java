// package com.x264.webmtotelegram.VideoUtils;

// import org.apache.commons.io.FileUtils;

// import java.io.File;
// import java.io.IOException;
// import java.net.MalformedURLException;
// import java.net.URI;
// import java.net.URL;
// import java.nio.file.Path;

// public class Downloader {
//     public static final String PATH_SAVE = System.getProperty("user.dir") + "/";
//     public static String DownloadFile(String URLVideo)
//     {
//         var fileName = Path.of(PATH_SAVE + URLVideo.substring(URLVideo.lastIndexOf("/") + 1) + ".mp4");
//         File file = fileName.toFile();
//         URL url = null;
//         try {
//             url = URI.create(URLVideo).toURL();
//         } catch (MalformedURLException e) {
//             e.printStackTrace();
//         }
//         try {
//             FileUtils.copyURLToFile(url, file);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         return fileName.toString();
//     }
// }
