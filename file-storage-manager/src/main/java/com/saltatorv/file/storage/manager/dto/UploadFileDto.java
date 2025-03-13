package com.saltatorv.file.storage.manager.dto;

import lombok.Getter;

import java.nio.file.Path;

public class UploadFileDto {
    @Getter
    private final Path fileName;
    @Getter
    private final String contentType;
    private final byte[] content;

    public UploadFileDto(Path fileName, String contentType, byte[] content) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.content = content;
    }

    public byte[] getContent() {
        return content.clone();
    }
}
