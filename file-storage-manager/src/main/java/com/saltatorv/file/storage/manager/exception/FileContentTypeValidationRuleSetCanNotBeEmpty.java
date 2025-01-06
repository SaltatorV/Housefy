package com.saltatorv.file.storage.manager.exception;

public class FileContentTypeValidationRuleSetCanNotBeEmpty extends FileStorageBaseException{

    private static final String MESSAGE = "Content types set provided to FileContentTypeValidationRule can not be empty";
    public FileContentTypeValidationRuleSetCanNotBeEmpty() {
        super(MESSAGE);
    }
}
