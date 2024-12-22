package com.saltatorv.file.storage.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class File {

    private String content;
    private Path fileDestination;

    private File(String content, Path fileDestination) {
        this.content = content;
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

        return new File(content, destination);
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
}
