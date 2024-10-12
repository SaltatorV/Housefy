package com.saltatorv.housefy.file.storage.manager;

class FileAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "File %s already exists exception.";

    private FileAlreadyExistsException(String filePath) {
        super(String.format(MESSAGE_TEMPLATE, filePath));
    }

    static FileAlreadyExistsException createForFile(String fileName) {
        return new FileAlreadyExistsException(fileName);
    }
}
