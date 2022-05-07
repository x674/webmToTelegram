package com.x264.webmtotelegram.videoutils;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {
    private Converter converter = new Converter();
    @Test
    void testConvertFromUrl()
    {
        File convertedFilePath = converter.convertWebmToMP4("https://2ch.hk/b/src/267570191/16518200661920.webm");
        assertNotNull(convertedFilePath);
        convertedFilePath.delete();
    }
}