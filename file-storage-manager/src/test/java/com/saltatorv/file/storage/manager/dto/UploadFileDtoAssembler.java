package com.saltatorv.file.storage.manager.dto;

import java.nio.file.Path;

import static com.saltatorv.file.storage.manager.dto.UploadFileDtoObjectMother.uploadTextFileDto;

public class UploadFileDtoAssembler implements UploadFileDtoDefaultValue {
    private Path fileName;
    private String contentType;
    private byte[] content;

    private UploadFileDtoAssembler() {
    }

    public static UploadFileDtoDefaultValue buildUploadFileDto() {
        return new UploadFileDtoAssembler();
    }

    @Override
    public UploadFileDtoAssembler uploadTextFileAsDefault() {
        UploadFileDto defaultValue = uploadTextFileDto();
        this.fileName = defaultValue.getFileName();
        this.contentType = defaultValue.getContentType();
        this.content = defaultValue.getContent();
        return this;
    }

    public UploadFileDtoAssembler butWithFileName(Path fileName) {
        this.fileName = fileName;
        return this;
    }

    public UploadFileDtoAssembler butWithFileName(String fileName) {
        this.fileName = Path.of(fileName);
        return this;
    }

    public UploadFileDtoAssembler butWithContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public UploadFileDtoAssembler butWithContent(String content) {
        this.content = content.getBytes();
        return this;
    }

    public UploadFileDto create() {
        return new UploadFileDto(fileName, contentType, content);
    }
}
