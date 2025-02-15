package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;
import com.saltatorv.file.storage.manager.exception.FileContainInvalidContentType;
import com.saltatorv.file.storage.manager.exception.FileContentTypeValidationRuleSetCanNotBeEmpty;

import java.util.Set;

public class FileContentTypeValidationRule implements FileValidationRule{
    private final Set<String> contentTypes;

    public FileContentTypeValidationRule(Set<String> contentTypes) {
        ensureContentTypesSetIsNotEmpty(contentTypes);
        this.contentTypes = contentTypes;
    }

    @Override
    public void validate(UploadFileDto uploadFileDto) {
        contentTypes
                .stream()
                .filter(type -> type.equals(uploadFileDto.getContentType()))
                .findAny()
                .orElseThrow(() -> new FileContainInvalidContentType(uploadFileDto.getContentType(), contentTypes));
    }

    private void ensureContentTypesSetIsNotEmpty(Set<String> contentTypes) {
        if (contentTypes.isEmpty()) {
            throw new FileContentTypeValidationRuleSetCanNotBeEmpty();
        }
    }
}
