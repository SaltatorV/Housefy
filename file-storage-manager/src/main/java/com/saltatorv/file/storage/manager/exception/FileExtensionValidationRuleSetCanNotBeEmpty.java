package com.saltatorv.file.storage.manager.exception;

public class FileExtensionValidationRuleSetCanNotBeEmpty extends FileStorageBaseException{

    private static final String MESSAGE = "Extensions set provided to FileExtensionValidationRule can not be empty";
    public FileExtensionValidationRuleSetCanNotBeEmpty() {
        super(MESSAGE);
    }
}
