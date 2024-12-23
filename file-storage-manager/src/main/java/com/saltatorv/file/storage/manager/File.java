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

    public static File upload(UploadFileCommand command) {
        createDirectoriesIfNeeded(command);

        try {
            Files.write(command.getFileName(), command.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new File(command.getDestination());
    }

    private static void createDirectoriesIfNeeded(UploadFileCommand command) {
        if (command.isCreateDirectories()) {
            try {
                Files.createDirectories(command.getDestination());
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
