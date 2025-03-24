package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;
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

    static SingleFileResult upload(UploadFileDto uploadFileDto, FileValidationRule validationRule) {
        try {
            validationRule.validate(uploadFileDto);
        } catch (FileStorageBaseException e) {
            return SingleFileResult.produceFailure(e.getMessage());
        }

        try {
            Files.createDirectories(uploadFileDto.getFileName().getParent());
            Files.write(uploadFileDto.getFileName(), uploadFileDto.getContent());
        } catch (IOException e) {
            return SingleFileResult.produceFailure("Can not write to file: %s".formatted(uploadFileDto.getFileName()));
        }

        return SingleFileResult.produceSuccess(new File(uploadFileDto.getFileName()));
    }

    public <T> T read(FileContentReader<T> fileReader) {
        return fileReader.read(fileName);
    }

    public boolean delete() {
        try {
            return Files.exists(fileName) && Files.deleteIfExists(fileName);
        } catch (IOException e) {
            return false;
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
