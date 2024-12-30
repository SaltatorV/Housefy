package com.saltatorv.file.storage.manager.vo;

public class Content {
    private final String content;

    public Content(String content) {
        this.content = content;
    }

    public String getAsString() {
        return content;
    }

    public byte[] getAsByteArray() {
        return content.getBytes();
    }

    public char[] getAsCharArray() {
        return content.toCharArray();
    }
}
