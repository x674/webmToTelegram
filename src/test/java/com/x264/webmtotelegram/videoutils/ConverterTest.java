package com.x264.webmtotelegram.videoutils;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {
    private Converter converter = new Converter();

    void testConvertFromUrl() {
        Optional<File> file = converter.convertWebmToMP4("https://file-examples.com/wp-content/uploads/2020/03/file_example_WEBM_480_900KB.webm");
        if (file.isPresent()) {
            File convertedFilePath = file.get();
            assertNotNull(convertedFilePath);
            convertedFilePath.delete();
        }
    }
}