package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import com.saltatorv.file.storage.manager.validation.FileValidationRule;
import com.saltatorv.file.storage.manager.vo.FileName;

import java.io.IOException;
import java.nio.file.Files;

public class File {
    private FileName fileName;

    public File(FileName fileName) {
        ensureFileExists(fileName);
        ensureDestinationPointToFile(fileName);
        this.fileName = fileName;
    }

    public static File upload(UploadFileCommand command, FileValidationRule validationRule) {
        validationRule.validate(command);
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
            Files.delete(fileName.getDestination());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void ensureFileExists(FileName fileName) {
        if (!Files.exists(fileName.getDestination())) {
            throw new RuntimeException("File: %s do not exists.".formatted(fileName));
        }
    }

    private void ensureDestinationPointToFile(FileName fileName) {
        if (!Files.isRegularFile(fileName.getDestination())) {
            throw new RuntimeException("Destination: %s do not point to regular file.".formatted(fileName));
        }
    }
}
