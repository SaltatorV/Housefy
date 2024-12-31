package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.vo.FileName;

public interface FileContentReader<T> {
    T read(FileName fileName);
}
