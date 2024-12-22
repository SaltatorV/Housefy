package com.saltatorv.file.storage.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class File {

    private String content;
    private String fileDestination;

    private File(String content, String fileDestination) {
        this.content = content;
        this.fileDestination = fileDestination;
    }

    public static void upload(String fileName, String fileDestination, String content, boolean createDirectories) {

        try {
            if (createDirectories) {
                Path path = Path.of(fileDestination);
                Files.createDirectories(path);
            }
            Path path = Path.of(fileDestination).resolve(Path.of(fileName));
            Files.write(path, content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
