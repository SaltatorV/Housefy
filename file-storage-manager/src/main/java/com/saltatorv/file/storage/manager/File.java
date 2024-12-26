package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.VO.Destination;

import java.io.IOException;
import java.nio.file.Files;

public class File {
    private Destination fileDestination;

    public File(Destination fileDestination) {
        ensureFileExists(fileDestination);
        ensureDestinationPointToFile(fileDestination);
        this.fileDestination = fileDestination;
    }

    public static File upload(UploadFileCommand command) {
        createDirectoriesIfNeeded(command);

        try {
            Files.write(command.getFileName().getDestination(), command.getContent());
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

    public <T> T read(FileContentReader<T> fileReader) {
        return fileReader.read(fileDestination);
    }

    public boolean delete() {
        try {
            Files.delete(fileDestination.getDestination());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void ensureFileExists(Destination fileDestination) {
        if (!Files.exists(fileDestination.getDestination())) {
            throw new RuntimeException("File: %s do not exists.".formatted(fileDestination));
        }
    }

    private void ensureDestinationPointToFile(Destination fileDestination) {
        if (!Files.isRegularFile(fileDestination.getDestination())) {
            throw new RuntimeException("Destination: %s do not point to regular file.".formatted(fileDestination));
        }
    }
}
