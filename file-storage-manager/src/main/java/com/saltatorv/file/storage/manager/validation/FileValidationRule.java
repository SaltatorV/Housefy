package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.UploadFileCommand;

public interface FileValidationRule {
    void validate(UploadFileCommand command);
}
