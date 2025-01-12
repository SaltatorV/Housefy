package com.saltatorv.file.storage.manager.command;

import lombok.Getter;

import java.nio.file.Path;

public class UploadFileCommand {
    @Getter
    private final Path fileName;
    @Getter
    private final String contentType;
    private final byte[] content;
    @Getter
    private final boolean createDirectories;

    public UploadFileCommand(Path fileName, String contentType, byte[] content, boolean createDirectories) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.content = content;
        this.createDirectories = createDirectories;
    }

    public byte[] getContent() {
        return content.clone();
    }
}
