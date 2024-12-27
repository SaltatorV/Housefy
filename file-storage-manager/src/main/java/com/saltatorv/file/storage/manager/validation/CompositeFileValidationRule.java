package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;

import java.util.List;

public class CompositeFileValidationRule implements FileValidationRule{

    private final List<FileValidationRule> fileValidationRules;

    public CompositeFileValidationRule(List<FileValidationRule> fileValidationRules) {
        this.fileValidationRules = fileValidationRules;
    }

    @Override
    public void validate(UploadFileCommand command) {
        fileValidationRules.forEach(rule -> rule.validate(command));
    }
}
