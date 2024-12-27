package com.saltatorv.file.storage.manager.validation;

import java.util.ArrayList;
import java.util.List;

public class CompositeFileValidationBuilder {

    private List<FileValidationRule> fileValidationRules;

    public CompositeFileValidationBuilder() {
        fileValidationRules = new ArrayList<>();
    }

    public CompositeFileValidationBuilder addFileValidationRule(FileValidationRule rule) {
        fileValidationRules.add(rule);
        return this;
    }

    public CompositeFileValidationRule create() {
        return new CompositeFileValidationRule(fileValidationRules);
    }
}
