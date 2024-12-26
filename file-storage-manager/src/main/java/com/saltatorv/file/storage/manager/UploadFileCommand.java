package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.VO.Destination;

import java.nio.file.Path;

public class UploadFileCommand {
    private final String fileName;
    private final Destination destination;
    private final byte[] content;
    private final boolean createDirectories;

    public UploadFileCommand(String fileName, String destination, byte[] content, boolean createDirectories) {
        this.fileName = fileName;
        this.destination = new Destination(destination);
        this.content = content;
        this.createDirectories = createDirectories;
    }

    public Destination getFileName() {
        return destination.resolve(fileName);
    }

    public Path getDestination() {
        return destination.getDestination();
    }

    public byte[] getContent() {
        return content;
    }

    public boolean isCreateDirectories() {
        return createDirectories;
    }
}
