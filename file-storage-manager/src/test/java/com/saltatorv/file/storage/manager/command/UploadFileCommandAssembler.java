package com.saltatorv.file.storage.manager.command;

import java.nio.file.Path;

import static com.saltatorv.file.storage.manager.command.UploadFileCommandObjectMother.uploadTextFileCommand;

public class UploadFileCommandAssembler implements UploadFileCommandDefaultValue {
    private Path fileName;
    private String contentType;
    private byte[] content;
    private boolean createDirectories;

    private UploadFileCommandAssembler() {
    }

    public static UploadFileCommandDefaultValue buildUploadFileCommand() {
        return new UploadFileCommandAssembler();
    }

    @Override
    public UploadFileCommandAssembler uploadTextFileAsDefault() {
        UploadFileCommand defaultValue = uploadTextFileCommand();
        this.fileName = defaultValue.getFileName();
        this.contentType = defaultValue.getContentType();
        this.content = defaultValue.getContent();
        this.createDirectories = defaultValue.isCreateDirectories();
        return this;
    }

    public UploadFileCommandAssembler butWithFileName(Path fileName) {
        this.fileName = fileName;
        return this;
    }

    public UploadFileCommandAssembler butWithFileName(String fileName) {
        this.fileName = Path.of(fileName);
        return this;
    }

    public UploadFileCommandAssembler butWithContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public UploadFileCommandAssembler butWithContent(String content) {
        this.content = content.getBytes();
        return this;
    }

    public UploadFileCommandAssembler shouldCreateDirectories() {
        this.createDirectories = true;
        return this;
    }

    public UploadFileCommandAssembler skipDirectoryCreation() {
        this.createDirectories = false;
        return this;
    }

    public UploadFileCommand create() {
        return new UploadFileCommand(fileName, contentType, content, createDirectories);
    }
}
