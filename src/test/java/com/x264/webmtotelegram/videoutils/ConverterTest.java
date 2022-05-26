package com.x264.webmtotelegram.videoutils;

import java.io.File;
import java.io.IOException;

import ws.schild.jave.EncoderException;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {
    private Converter converter = new Converter();

    void testConvertFromUrl() {
        File file;
        try {
            file = converter.convertWebmToMP4("https://file-examples.com/wp-content/uploads/2020/03/file_example_WEBM_480_900KB.webm");
            assertNotNull(file);
            file.delete();
        } catch (IllegalArgumentException | IOException | EncoderException e) {
            e.printStackTrace();
        }

    }
}