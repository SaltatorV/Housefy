package com.saltatorv.file.storage.manager;

import java.util.List;

public class MultipleFilesResult extends FileResult<List<File>> {

    private MultipleFilesResult(List<File> value, boolean isSuccess, String failureCause) {
        super(value, isSuccess, failureCause);
    }

    public static MultipleFilesResult produceSuccess(List<File> files) {
        return new MultipleFilesResult(files, true, null);
    }

    public static MultipleFilesResult produceFailure(String failureCause) {
        return new MultipleFilesResult(null, false, failureCause);
    }

    @Override
    List<File> processValue(List<File> value) {
        return value;
    }

}
