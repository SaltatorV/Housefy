package com.saltatorv.file.storage.manager.exception;

import java.nio.file.Path;

public class FileNotFoundException extends FileStorageBaseException {
    private static final String MESSAGE = "File: %s do not exists.";
    public FileNotFoundException(Path fileName) {
        super(MESSAGE.formatted(fileName));
    }
}
