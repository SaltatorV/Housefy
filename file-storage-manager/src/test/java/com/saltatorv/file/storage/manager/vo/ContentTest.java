package com.saltatorv.file.storage.manager.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContentTest {

    Content content;

    @BeforeEach
    public void setup() {
        content = null;
    }

    @Test
    @DisplayName("Can get content as string")
    public void canGetContentAsString() {
        //given
        createContent("Test content");

        //when
        var actualContent = getContentAsString();

        //then
        assertEquals(actualContent, "Test content");
    }

    @Test
    @DisplayName("Can get content as byte array")
    public void canGetContentAsByteArray() {
        //given
        createContent("Test content");

        //when
        var actualContent = getContentAsByteArray();

        //then
        assertArrayEquals(actualContent, "Test content".getBytes());
    }

    @Test
    @DisplayName("Can get content as char array")
    public void canGetContentAsCharArray() {
        //given
        createContent("Test content");

        //when
        var actualContent = getContentAsCharArray();

        //then
        assertArrayEquals(actualContent, "Test content".toCharArray());
    }

    private void createContent(String contentValue) {
        content = new Content(contentValue);
    }

    private String getContentAsString() {
        return content.getAsString();
    }

    private byte[] getContentAsByteArray() {
        return content.getAsByteArray();
    }

    private char[] getContentAsCharArray() {
        return content.getAsCharArray();
    }
}
