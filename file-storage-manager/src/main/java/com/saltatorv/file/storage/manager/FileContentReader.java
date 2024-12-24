package com.saltatorv.file.storage.manager;

public interface FileContentReader<T> {
    T read(Destination fileDestination);
}
