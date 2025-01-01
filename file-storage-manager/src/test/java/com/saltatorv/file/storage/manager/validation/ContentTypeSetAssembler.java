package com.saltatorv.file.storage.manager.validation;

import java.util.HashSet;
import java.util.Set;

public class ContentTypeSetAssembler {

    private Set<String> contentTypes;

    private ContentTypeSetAssembler() {
        contentTypes = new HashSet<>();
    }

    public static ContentTypeSetAssembler buildContentTypes() {
        return new ContentTypeSetAssembler();
    }

    public ContentTypeSetAssembler addContentType(String contentType) {
        contentTypes.add(contentType);
        return this;
    }

    public Set<String> create() {
        return contentTypes;
    }

}
