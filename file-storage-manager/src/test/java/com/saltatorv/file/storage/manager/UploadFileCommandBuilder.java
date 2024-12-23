package com.saltatorv.file.storage.manager;

class UploadFileCommandBuilder {
    private String fileName;
    private String destination;
    private String content;
    private boolean createDirectories;

    private UploadFileCommandBuilder() {
    }

    static UploadFileCommandBuilder buildUploadFileCommand(String fileName) {
        UploadFileCommandBuilder builder = new UploadFileCommandBuilder();
        builder.withFileName(fileName);
        return builder;
    }

    UploadFileCommandBuilder withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    UploadFileCommandBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }


    UploadFileCommandBuilder withContent(String content) {
        this.content = content;
        return this;
    }


    UploadFileCommandBuilder withCreateDirectories(boolean createDirectories) {
        this.createDirectories = createDirectories;
        return this;
    }

    UploadFileCommand create() {
        return new UploadFileCommand(fileName, destination, content.getBytes(), createDirectories);
    }
}
