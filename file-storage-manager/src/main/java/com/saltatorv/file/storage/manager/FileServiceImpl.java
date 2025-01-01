package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;

import java.nio.file.Path;
import java.util.List;

public class FileServiceImpl implements FileService{
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
        return null;
    }
}
