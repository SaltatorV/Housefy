package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.vo.Destination;

public interface FileContentReader<T> {
    T read(Destination fileDestination);
}
