package com.saltatorv.file.storage.manager.exception;

import java.nio.file.Path;

public class FilesUnavailableException extends FileStorageBaseException {
    private static final String MESSAGE = "Can not get files from: %s, path is unavailable.";
    public FilesUnavailableException(Path fileName) {
        super(MESSAGE.formatted(fileName));
    }
}
