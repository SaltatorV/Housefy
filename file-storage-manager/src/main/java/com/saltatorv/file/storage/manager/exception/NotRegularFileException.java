package com.saltatorv.file.storage.manager.exception;

import java.nio.file.Path;

public class NotRegularFileException extends FileStorageBaseException {
    private static final String MESSAGE = "Destination: %s do not point to regular file.";
    public NotRegularFileException(Path fileName) {
        super(MESSAGE.formatted(fileName));
    }
}
