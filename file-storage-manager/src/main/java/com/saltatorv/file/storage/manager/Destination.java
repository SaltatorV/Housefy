package com.saltatorv.file.storage.manager;

import java.nio.file.Path;

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
}
