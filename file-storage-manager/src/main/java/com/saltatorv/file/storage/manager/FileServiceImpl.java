package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import com.saltatorv.file.storage.manager.validation.FileValidationRule;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final FileValidationRule validationRule;

    @Override
    public List<File> getFiles(Path directoryPath) {
        return null;
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
