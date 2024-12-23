package com.saltatorv.file.storage.manager;

import java.nio.file.Path;

class Destination {
    private final Path destination;

    Destination(String destination) {
        this.destination = Path.of(destination);
    }

    Path resolve(String fileName) {
        return destination.resolve(fileName);
    }

    Path getDestination() {
        return destination;
    }
}
