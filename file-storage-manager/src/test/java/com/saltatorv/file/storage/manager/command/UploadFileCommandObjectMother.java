package com.saltatorv.file.storage.manager.command;

import java.nio.file.Path;

public class UploadFileCommandObjectMother {
    public static UploadFileCommand uploadTextFileCommand() {
        return new UploadFileCommand(Path.of("test.txt"), "Test content".getBytes(), true);
    }
}
