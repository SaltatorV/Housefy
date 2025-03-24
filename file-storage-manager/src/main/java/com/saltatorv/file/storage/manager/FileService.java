package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;

import java.nio.file.Path;

public interface FileService {

    MultipleFilesResult getFiles(Path directoryPath);

    SingleFileResult getFile(Path filePath);

    SingleFileResult uploadFile(UploadFileDto uploadFileDto);
}
