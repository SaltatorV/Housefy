package com.saltatorv.file.storage.manager.vo;

import java.nio.file.Path;

public class Extension {
    private final String extension;

    public Extension(String extension) {
        this.extension = ensureExtensionStartsWithDot(extension);
    }

    public boolean isIncludedIn(Path fileName) {
        return fileName.toString().endsWith(extension);
    }

    private String ensureExtensionStartsWithDot(String extension) {
        if (!extension.startsWith(".")) {
            extension = String.join("", ".", extension);
        }
        return extension;
    }
}
