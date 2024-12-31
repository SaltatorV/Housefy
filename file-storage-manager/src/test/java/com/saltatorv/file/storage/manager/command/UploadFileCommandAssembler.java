package com.saltatorv.file.storage.manager.command;

import com.saltatorv.file.storage.manager.vo.FileName;

import java.nio.file.Path;

public class UploadFileCommandAssembler {
    private Path fileName;
    private String destination;
    private String content;
    private boolean createDirectories;

    private UploadFileCommandAssembler() {
    }

    public static UploadFileCommandAssembler buildUploadFileCommand(Path fileName) {
        UploadFileCommandAssembler builder = new UploadFileCommandAssembler();
        builder.withFileName(fileName);
        return builder;
    }

    public static UploadFileCommandAssembler buildUploadFileCommand(String fileName) {
        return buildUploadFileCommand(Path.of(fileName));
    }

    public UploadFileCommandAssembler withFileName(Path fileName) {
        this.fileName = fileName;
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
        return new UploadFileCommand(new FileName(fileName), content.getBytes(), createDirectories);
    }
}
