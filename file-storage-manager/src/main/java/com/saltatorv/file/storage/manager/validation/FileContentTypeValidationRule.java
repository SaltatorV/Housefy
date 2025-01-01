package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;

import java.util.Set;

public class FileContentTypeValidationRule implements FileValidationRule{
    private final Set<String> contentTypes;

    public FileContentTypeValidationRule(Set<String> contentTypes) {
        ensureContentTypesSetIsNotEmpty(contentTypes);
        this.contentTypes = contentTypes;
    }

    @Override
    public void validate(UploadFileCommand command) {
        contentTypes
                .stream()
                .filter(type -> type.equals(command.getContentType()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("File contain invalid content type"));
    }

    private void ensureContentTypesSetIsNotEmpty(Set<String> contentTypes) {
        if (contentTypes.isEmpty()) {
            throw new RuntimeException("Content types set provided to FileContentTypeValidationRule can not be empty");
        }
    }
}
