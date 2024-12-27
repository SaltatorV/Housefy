package com.saltatorv.file.storage.manager.validation;

import java.util.ArrayList;
import java.util.List;

public class CompositeFileValidationAssembler {

    private List<FileValidationRule> fileValidationRules;

    public CompositeFileValidationAssembler() {
        fileValidationRules = new ArrayList<>();
    }

    public CompositeFileValidationAssembler addFileValidationRule(FileValidationRule rule) {
        fileValidationRules.add(rule);
        return this;
    }

    public CompositeFileValidationRule create() {
        return new CompositeFileValidationRule(fileValidationRules);
    }
}
