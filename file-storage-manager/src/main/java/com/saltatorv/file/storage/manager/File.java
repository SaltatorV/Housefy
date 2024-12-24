package com.saltatorv.file.storage.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class File {
    private Path fileDestination;

    public File(Path fileDestination) {
        ensureFileExists(fileDestination);
        ensureDestinationPointToFile(fileDestination);
        this.fileDestination = fileDestination;
    }

    public static File upload(UploadFileCommand command) {
        createDirectoriesIfNeeded(command);

        try {
            Files.write(command.getFileName(), command.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new File(command.getFileName());
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

    public boolean delete() {
        try {
            Files.delete(fileDestination);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void ensureFileExists(Path fileDestination) {
        if (!Files.exists(fileDestination)) {
            throw new RuntimeException("File: %s do not exists.".formatted(fileDestination));
        }
    }

    private void ensureDestinationPointToFile(Path fileDestination) {
        if (!Files.isRegularFile(fileDestination)) {
            throw new RuntimeException("Destination: %s do not point to regular file.".formatted(fileDestination));
        }
    }
}
