package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;

import java.nio.file.Path;
import java.util.List;

public interface FileService {

    List<File> getFiles(Path directoryPath);
    File getFile(Path filePath);
    File uploadFile(UploadFileDto uploadFileDto);
}
