package com.saltatorv.file.storage.manager.command;

import com.saltatorv.file.storage.manager.vo.Destination;
import lombok.Getter;

public class UploadFileCommand {
    private final String fileName;
    @Getter
    private final Destination destination;
    private final byte[] content;
    @Getter
    private final boolean createDirectories;

    public UploadFileCommand(String fileName, String destination, byte[] content, boolean createDirectories) {
        this.fileName = fileName;
        this.destination = new Destination(destination);
        this.content = content;
        this.createDirectories = createDirectories;
    }

    public Destination getFileName() {
        return destination.resolve(fileName);
    }

    public byte[] getContent() {
        return content;
    }

}
