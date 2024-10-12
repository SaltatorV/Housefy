package com.saltatorv.housefy.file.storage.manager;

import com.saltatorv.housefy.file.storage.manager.dto.UploadFileData;

class UploadFileDataBuilder {
    private String fileName;
    private String destination;
    private byte[] content;

    static UploadFileDataBuilder buildUploadFileData() {
        return new UploadFileDataBuilder();
    }

    UploadFileDataBuilder withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    UploadFileDataBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    UploadFileDataBuilder withContent(byte[] content) {
        this.content = content;
        return this;
    }

    UploadFileData create() {
        return new UploadFileData(fileName, destination, content);
    }
}
