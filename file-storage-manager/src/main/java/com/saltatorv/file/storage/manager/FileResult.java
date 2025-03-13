package com.saltatorv.file.storage.manager;

import lombok.Getter;

public abstract class FileResult<T> {
    private final T value;
    private final boolean isSuccess;
    @Getter
    private final String failureCause;

    FileResult(T value, boolean isSuccess, String failureCause) {
        this.value = value;
        this.isSuccess = isSuccess;
        this.failureCause = failureCause;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public T getValue() {
        return processValue(value);
    }

    abstract T processValue(T value);
}
