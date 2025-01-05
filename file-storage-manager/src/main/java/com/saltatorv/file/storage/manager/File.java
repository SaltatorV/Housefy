package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import com.saltatorv.file.storage.manager.exception.DirectoryUnavailableException;
import com.saltatorv.file.storage.manager.exception.FileNotFoundException;
import com.saltatorv.file.storage.manager.exception.FileStorageBaseException;
import com.saltatorv.file.storage.manager.exception.NotRegularFileException;
import com.saltatorv.file.storage.manager.validation.FileValidationRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class File {
    private Path fileName;

    public File(Path fileName) {
        ensureFileExists(fileName);
        ensureDestinationPointToFile(fileName);
        this.fileName = fileName;
    }

    static File upload(UploadFileCommand command, FileValidationRule validationRule) {
        validationRule.validate(command);
        createDirectoriesIfNeeded(command);

        try {
            Files.write(command.getFileName(), command.getContent());
        } catch (IOException e) {
            throw new FileStorageBaseException("Can not write to file: %s".formatted(command.getFileName()));
        }

        return new File(command.getFileName());
    }

    private static void createDirectoriesIfNeeded(UploadFileCommand command) {
        Path parentDirectory = command.getFileName().getParent();

        if (command.isCreateDirectories()) {
            try {
                Files.createDirectories(parentDirectory);
            } catch (IOException e) {
                throw new FileStorageBaseException("Can not create directories: %s".formatted(parentDirectory));
            }
        } else if (directoryIsUnavailable(parentDirectory)) {
            throw new DirectoryUnavailableException(command.getFileName());
        }
    }

    private static boolean directoryIsUnavailable(Path parentDirectory) {
        return !Files.exists(parentDirectory);
    }

    public <T> T read(FileContentReader<T> fileReader) {
        return fileReader.read(fileName);
    }

    public boolean delete() {
        try {
            if (Files.exists(fileName)) {
                Files.delete(fileName);
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new FileStorageBaseException("Can not delete file: %s".formatted(fileName));
        }
    }

    private void ensureFileExists(Path fileName) {
        if (!Files.exists(fileName)) {
            throw new FileNotFoundException(fileName);
        }
    }

    private void ensureDestinationPointToFile(Path fileName) {
        if (!Files.isRegularFile(fileName)) {
            throw new NotRegularFileException(fileName);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(fileName, file.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }
}
