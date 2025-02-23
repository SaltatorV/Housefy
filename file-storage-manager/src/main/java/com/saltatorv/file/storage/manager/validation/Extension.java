package com.saltatorv.file.storage.manager.validation;

import java.nio.file.Path;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Extension extension1 = (Extension) o;
        return Objects.equals(extension, extension1.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extension);
    }

    @Override
    public String toString() {
        return extension;
    }
}
