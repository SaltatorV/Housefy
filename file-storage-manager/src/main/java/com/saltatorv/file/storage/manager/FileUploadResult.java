package com.saltatorv.file.storage.manager;

import lombok.Getter;

public class FileUploadResult {
    @Getter
    private final File value;
    private final boolean isSuccess;
    @Getter
    private final String failureCause;

    private FileUploadResult(File value, boolean isSuccess, String failureCause) {
        this.value = value;
        this.isSuccess = isSuccess;
        this.failureCause = failureCause;
    }

    public static FileUploadResult createForSuccess(File file) {
        return new FileUploadResult(file, true, null);
    }

    public static FileUploadResult createForFailure(String failureCause) {
        return new FileUploadResult(null, false, failureCause);
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
