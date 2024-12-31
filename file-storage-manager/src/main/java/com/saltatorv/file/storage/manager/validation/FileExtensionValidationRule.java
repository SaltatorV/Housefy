package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import com.saltatorv.file.storage.manager.vo.Extension;

import java.util.Set;

public class FileExtensionValidationRule implements FileValidationRule {
    private final Set<Extension> extensions;

    public FileExtensionValidationRule(Set<Extension> extensions) {
        ensureExtensionSetIsNotEmpty(extensions);
        this.extensions = extensions;
    }

    private void ensureExtensionSetIsNotEmpty(Set<Extension> extensions) {
        if (extensions.isEmpty()) {
            throw new RuntimeException("Extension set provided to ExtensionFileValidationRule can not be empty");
        }
    }

    @Override
    public void validate(UploadFileCommand command) {
        extensions
                .stream()
                .filter(extension -> extension.isIncludedIn(command.getFileName()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("File contain invalid extension"));
    }
}
