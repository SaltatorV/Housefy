package com.saltatorv.file.storage.manager;

import lombok.Getter;

public class FileResult {
    @Getter
    private final File value;
    private final boolean isSuccess;
    @Getter
    private final String failureCause;

    private FileResult(File value, boolean isSuccess, String failureCause) {
        this.value = value;
        this.isSuccess = isSuccess;
        this.failureCause = failureCause;
    }

    public static FileResult produceSuccess(File file) {
        return new FileResult(file, true, null);
    }

    public static FileResult produceFailure(String failureCause) {
        return new FileResult(null, false, failureCause);
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
