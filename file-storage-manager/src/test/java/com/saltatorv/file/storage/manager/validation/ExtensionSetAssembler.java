package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.vo.Extension;

import java.util.HashSet;
import java.util.Set;

public class ExtensionSetAssembler {

    private Set<Extension> extensions;

    private ExtensionSetAssembler() {
        extensions = new HashSet<>();
    }

    public static ExtensionSetAssembler buildExtensions() {
        return new ExtensionSetAssembler();
    }

    public ExtensionSetAssembler addExtension(Extension extension) {
        extensions.add(extension);
        return this;
    }

    public Set<Extension> create() {
        return extensions;
    }

}
