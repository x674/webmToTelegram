package com.x264.webmtotelegram.videoutils;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {
    private Converter converter = new Converter();
    @Test
    void testConvertFromUrl()
    {
        File convertedFilePath = converter.convertWebmToMP4("https://file-examples.com/wp-content/uploads/2020/03/file_example_WEBM_480_900KB.webm");
        assertNotNull(convertedFilePath);
        convertedFilePath.delete();
    }
}