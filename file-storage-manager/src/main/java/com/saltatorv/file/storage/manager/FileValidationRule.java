package com.saltatorv.file.storage.manager;

public interface FileValidationRule {
    void validate(UploadFileCommand command);
}
