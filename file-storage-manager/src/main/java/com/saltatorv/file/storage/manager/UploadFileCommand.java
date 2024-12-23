package com.saltatorv.file.storage.manager;

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

    String getFileName() {
        return fileName;
    }

    Destination getDestination() {
        return destination;
    }

    byte[] getContent() {
        return content;
    }

    boolean isCreateDirectories() {
        return createDirectories;
    }
}
