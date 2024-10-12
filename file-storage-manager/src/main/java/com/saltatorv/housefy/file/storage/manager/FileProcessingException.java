package com.saltatorv.housefy.file.storage.manager;

class FileProcessingException extends RuntimeException {
    private FileProcessingException(String message) {
        super(message);
    }

    static FileProcessingException createForFile(String fileName) {
        return new FileProcessingException("Unable to process file: %s".formatted(fileName));
    }
}
