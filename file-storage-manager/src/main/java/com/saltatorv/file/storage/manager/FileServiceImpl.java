package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import com.saltatorv.file.storage.manager.exception.FileStorageBaseException;
import com.saltatorv.file.storage.manager.exception.FilesUnavailableException;
import com.saltatorv.file.storage.manager.validation.FileValidationRule;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileValidationRule validationRule;

    @Override
    public List<File> getFiles(Path directoryPath) {
        try (Stream<Path> filePaths = Files.list(directoryPath)) {
            return filePaths.map(File::new).toList();
        } catch (IOException e) {
            throw new FileStorageBaseException("Files from: %s are currently unavailable".formatted(directoryPath));
        }
    }

    @Override
    public File getFile(Path filePath) {
        return new File(filePath);
    }

    @Override
    public File uploadFile(UploadFileCommand uploadFileCommand) {
        return File.upload(uploadFileCommand, validationRule);
    }
}
