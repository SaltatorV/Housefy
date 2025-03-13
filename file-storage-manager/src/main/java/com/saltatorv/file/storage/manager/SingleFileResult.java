package com.saltatorv.file.storage.manager;

public class SingleFileResult extends FileResult<File> {

    private SingleFileResult(File value, boolean isSuccess, String failureCause) {
        super(value, isSuccess, failureCause);
    }

    public static SingleFileResult produceSuccess(File file) {
        return new SingleFileResult(file, true, null);
    }

    public static SingleFileResult produceFailure(String failureCause) {
        return new SingleFileResult(null, false, failureCause);
    }

    @Override
    File processValue(File value) {
        return value;
    }


}
