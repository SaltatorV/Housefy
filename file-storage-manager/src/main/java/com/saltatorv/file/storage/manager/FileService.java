package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;

import java.nio.file.Path;
import java.util.List;

public interface FileService {

    List<File> getFilesFrom(Path directoryPath);
    File getFile(Path filePath);
    File uploadFile(UploadFileCommand uploadFileCommand);
}
