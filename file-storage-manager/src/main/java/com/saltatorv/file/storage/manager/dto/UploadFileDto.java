package com.saltatorv.file.storage.manager.dto;

import lombok.Getter;

import java.nio.file.Path;

public class UploadFileDto {
    @Getter
    private final Path fileName;
    @Getter
    private final String contentType;
    private final byte[] content;
    @Getter
    private final boolean createDirectories;

    public UploadFileDto(Path fileName, String contentType, byte[] content, boolean createDirectories) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.content = content;
        this.createDirectories = createDirectories;
    }

    public byte[] getContent() {
        return content.clone();
    }
}
