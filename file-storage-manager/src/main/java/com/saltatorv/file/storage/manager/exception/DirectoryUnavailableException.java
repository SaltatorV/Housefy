package com.saltatorv.file.storage.manager.exception;

import java.nio.file.Path;

public class DirectoryUnavailableException extends FileStorageBaseException {
    private static final String MESSAGE = "Can not upload file: %s, parent directory do not exists and isCreateDirectories flag is false.";
    public DirectoryUnavailableException(Path fileName) {
        super(MESSAGE.formatted(fileName));
    }
}
