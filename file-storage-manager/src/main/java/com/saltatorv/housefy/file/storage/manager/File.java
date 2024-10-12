package com.saltatorv.housefy.file.storage.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class File {

    private final Path destination;
    private final String fileName;


    private File(Path destination, String fileName) {
        this.destination = destination;
        this.fileName = fileName;
    }

    static File initialize(Path destination, String fileName) {
        return new File(destination, fileName);
    }

    public void create(byte[] content) {
        Path filePath = destination.resolve(fileName);

        ensureFileNotExists(filePath);

        try {
            Files.createDirectories(destination);
            Files.write(filePath, content);
        } catch (IOException e) {
            throw FileProcessingException.createForFile(fileName);
        }
    }

    private void ensureFileNotExists(Path filePath) {
        if (Files.exists(filePath)) {
            throw FileAlreadyExistsException.createForFile(fileName);
        }
    }
}
