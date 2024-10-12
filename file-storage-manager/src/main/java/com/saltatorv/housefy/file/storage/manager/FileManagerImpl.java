package com.saltatorv.housefy.file.storage.manager;


import com.saltatorv.housefy.file.storage.manager.dto.UploadFileData;

import java.nio.file.Path;

class FileManagerImpl implements FileManager {

    private Path destinationDirectory;

    public FileManagerImpl(String destinationDirectory) {
        this.destinationDirectory = Path.of(destinationDirectory);
    }

    @Override
    public void uploadFile(UploadFileData uploadFileData) {
        Path uploadPath = this.destinationDirectory.resolve(uploadFileData.getDestination());
        File file = File.initialize(uploadPath, uploadFileData.getFileName());

        file.create(uploadFileData.getContent());
    }
}
