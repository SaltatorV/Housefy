package com.saltatorv.file.storage.manager.vo;

import lombok.Getter;

import java.nio.file.Path;
import java.util.Objects;

public class FileName {
    @Getter
    private final Path destination;

    public FileName(Path fileName) {
        this.destination = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileName that = (FileName) o;
        return Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination);
    }

    public Path getParent() {
        return destination.getParent();
    }
}
