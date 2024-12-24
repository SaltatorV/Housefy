package com.saltatorv.file.storage.manager;

import java.nio.file.Path;

class Destination {
    private final Path destination;

    Destination(String destination) {
        this.destination = Path.of(destination);
    }

    Destination(Path destination) {
        this.destination = destination;
    }

    Destination resolve(String path) {
        return new Destination(destination.resolve(path));
    }

    Path getDestination() {
        return destination;
    }
}
