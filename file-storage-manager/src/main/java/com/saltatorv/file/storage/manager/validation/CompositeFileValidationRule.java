package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;

import java.util.List;

public class CompositeFileValidationRule implements FileValidationRule{

    private final List<FileValidationRule> fileValidationRules;

    public CompositeFileValidationRule(List<FileValidationRule> fileValidationRules) {
        this.fileValidationRules = fileValidationRules;
    }

    @Override
    public void validate(UploadFileDto uploadFileDto) {
        fileValidationRules.forEach(rule -> rule.validate(uploadFileDto));
    }
}
