package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import com.saltatorv.file.storage.manager.validation.FileValidationRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class File {
    private Path fileName;

    public File(Path fileName) {
        ensureFileExists(fileName);
        ensureDestinationPointToFile(fileName);
        this.fileName = fileName;
    }

    public static File upload(UploadFileCommand command, FileValidationRule validationRule) {
        validationRule.validate(command);
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
                Files.createDirectories(command.getFileName().getParent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> T read(FileContentReader<T> fileReader) {
        return fileReader.read(fileName);
    }

    public boolean delete() {
        try {
            Files.delete(fileName);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void ensureFileExists(Path fileName) {
        if (!Files.exists(fileName)) {
            throw new RuntimeException("File: %s do not exists.".formatted(fileName));
        }
    }

    private void ensureDestinationPointToFile(Path fileName) {
        if (!Files.isRegularFile(fileName)) {
            throw new RuntimeException("Destination: %s do not point to regular file.".formatted(fileName));
        }
    }
}
