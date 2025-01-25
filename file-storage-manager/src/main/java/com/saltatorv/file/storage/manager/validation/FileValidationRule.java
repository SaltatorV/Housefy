package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;

public interface FileValidationRule {
    void validate(UploadFileDto uploadFileDto);
}
