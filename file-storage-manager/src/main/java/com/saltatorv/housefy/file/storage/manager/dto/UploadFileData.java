package com.saltatorv.housefy.file.storage.manager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class UploadFileData {
    private final String fileName;
    private final String destination;
    @Getter
    private final byte[] content;

    public String getFileName() {
        return fileName;
    }

    public Path getDestination() {
        return Paths.get(destination);
    }
}
