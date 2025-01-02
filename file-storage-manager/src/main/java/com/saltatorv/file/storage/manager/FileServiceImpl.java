package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
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
            throw new RuntimeException(e);
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
