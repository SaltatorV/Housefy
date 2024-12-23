package com.saltatorv.file.storage.manager;

class UploadFileCommandObjectMother {

    static UploadFileCommand createTextFileWithTestContentInTempDirectoryCommand() {
        return new UploadFileCommand("test.txt", "temp", "Test content".getBytes(), true);
    }
}
