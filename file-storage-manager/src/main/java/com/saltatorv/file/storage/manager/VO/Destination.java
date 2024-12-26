package com.saltatorv.file.storage.manager.VO;

import java.nio.file.Path;
import java.util.Objects;

public class Destination {
    private final Path destination;

    public Destination(String destination) {
        this.destination = Path.of(destination);
    }

    public Destination(Path destination) {
        this.destination = destination;
    }

    public Destination resolve(String path) {
        return new Destination(destination.resolve(path));
    }

    public Path getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destination that = (Destination) o;
        return Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination);
    }
}
