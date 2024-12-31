package com.saltatorv.file.storage.manager;


import java.nio.file.Path;

public interface FileContentReader<T> {
    T read(Path fileName);
}
