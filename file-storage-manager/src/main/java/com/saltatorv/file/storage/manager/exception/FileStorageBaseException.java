package com.saltatorv.file.storage.manager.exception;

public class FileStorageBaseException extends RuntimeException {
    public FileStorageBaseException(String message) {
        super(message);
    }

    public FileStorageBaseException(Throwable cause) {
        super(cause);
    }
}
