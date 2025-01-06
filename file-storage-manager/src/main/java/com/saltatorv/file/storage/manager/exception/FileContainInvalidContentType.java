package com.saltatorv.file.storage.manager.exception;

import java.util.Set;

public class FileContainInvalidContentType extends FileStorageBaseException {

    private static final String MESSAGE = "Uploading file contain invalid content type: %s, expected: %s";

    public FileContainInvalidContentType(String invalidContentType, Set<String> expectedContentTypes) {
        super(MESSAGE.formatted(invalidContentType, expectedContentTypes));
    }
}
