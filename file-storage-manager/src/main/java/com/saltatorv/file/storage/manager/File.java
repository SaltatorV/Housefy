package com.saltatorv.file.storage.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class File {
    private Path fileDestination;

    public File(Path fileDestination) {
        ensureFileExists(fileDestination);
        this.fileDestination = fileDestination;
    }

    public static File upload(String fileName, String fileDestination, String content, boolean createDirectories) {
        Path destination = Path.of(fileDestination);
        createDirectoriesIfNeeded(destination, createDirectories);

        try {
            destination = destination.resolve(Path.of(fileName));
            Files.write(destination, content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new File(destination);
    }

    private static void createDirectoriesIfNeeded(Path fileDestination, boolean createDirectories) {
        if (createDirectories) {
            try {
                Files.createDirectories(fileDestination);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void ensureFileExists(Path fileDestination) {
        if (!Files.exists(fileDestination)) {
            throw new RuntimeException("File: %s do not exists.".formatted(fileDestination));
        }
    }
}
