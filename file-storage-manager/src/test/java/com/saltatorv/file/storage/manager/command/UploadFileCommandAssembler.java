package com.saltatorv.file.storage.manager.command;

public class UploadFileCommandAssembler {
    private String fileName;
    private String destination;
    private String content;
    private boolean createDirectories;

    private UploadFileCommandAssembler() {
    }

    public static UploadFileCommandAssembler buildUploadFileCommand(String fileName) {
        UploadFileCommandAssembler builder = new UploadFileCommandAssembler();
        builder.withFileName(fileName);
        return builder;
    }

    public UploadFileCommandAssembler withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public UploadFileCommandAssembler withDestination(String destination) {
        this.destination = destination;
        return this;
    }


    public UploadFileCommandAssembler withContent(String content) {
        this.content = content;
        return this;
    }


    public UploadFileCommandAssembler withCreateDirectories(boolean createDirectories) {
        this.createDirectories = createDirectories;
        return this;
    }

    public UploadFileCommand create() {
        return new UploadFileCommand(fileName, destination, content.getBytes(), createDirectories);
    }
}
