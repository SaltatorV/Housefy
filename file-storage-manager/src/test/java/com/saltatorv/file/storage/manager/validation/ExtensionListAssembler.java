package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.vo.Extension;

import java.util.ArrayList;
import java.util.List;

public class ExtensionListAssembler {

    private List<Extension> extensions;

    public ExtensionListAssembler() {
        extensions = new ArrayList<>();
    }

    public ExtensionListAssembler addExtension(String extension) {
        extensions.add(new Extension(extension));
        return this;
    }

    public List<Extension> create() {
        return extensions;
    }

}
