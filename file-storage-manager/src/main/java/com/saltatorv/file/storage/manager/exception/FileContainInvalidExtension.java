package com.saltatorv.file.storage.manager.exception;

import com.saltatorv.file.storage.manager.validation.Extension;

import java.nio.file.Path;
import java.util.Set;

public class FileContainInvalidExtension extends FileStorageBaseException {

    private static final String MESSAGE = "Uploading file: %s contain invalid extension, expected: %s";

    public FileContainInvalidExtension(Path fileName, Set<Extension> expectedExtensions) {
        super(MESSAGE.formatted(fileName, expectedExtensions));
    }
}
