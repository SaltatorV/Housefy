package com.saltatorv.file.storage.manager.command;

public class UploadFileCommandBuilder {
    private String fileName;
    private String destination;
    private String content;
    private boolean createDirectories;

    private UploadFileCommandBuilder() {
    }

    public static UploadFileCommandBuilder buildUploadFileCommand(String fileName) {
        UploadFileCommandBuilder builder = new UploadFileCommandBuilder();
        builder.withFileName(fileName);
        return builder;
    }

    public UploadFileCommandBuilder withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public UploadFileCommandBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }


    public UploadFileCommandBuilder withContent(String content) {
        this.content = content;
        return this;
    }


    public UploadFileCommandBuilder withCreateDirectories(boolean createDirectories) {
        this.createDirectories = createDirectories;
        return this;
    }

    public UploadFileCommand create() {
        return new UploadFileCommand(fileName, destination, content.getBytes(), createDirectories);
    }
}
