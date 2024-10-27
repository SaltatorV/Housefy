package com.saltatorv.housefy.file.storage.manager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class UploadFileData {
    @Getter
    private final String fileName;
    @Getter
    private final byte[] content;
    private final String destination;

    public Path getDestination() {
        return Paths.get(destination);
    }
}
