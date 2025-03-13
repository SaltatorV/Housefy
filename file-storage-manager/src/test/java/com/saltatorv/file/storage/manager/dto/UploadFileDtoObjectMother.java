package com.saltatorv.file.storage.manager.dto;

import java.nio.file.Path;

public class UploadFileDtoObjectMother {
    public static UploadFileDto uploadTextFileDto() {
        return new UploadFileDto(Path.of("test.txt"), "text/plain", "Test content".getBytes());
    }
}
