package com.saltatorv.file.storage.manager.command;

import com.saltatorv.file.storage.manager.vo.FileName;
import lombok.Getter;

public class UploadFileCommand {
    @Getter
    private final FileName fileName;
    private final byte[] content;
    @Getter
    private final boolean createDirectories;

    public UploadFileCommand(FileName fileName, byte[] content, boolean createDirectories) {
        this.fileName = fileName;
        this.content = content;
        this.createDirectories = createDirectories;
    }

    public byte[] getContent() {
        return content.clone();
    }

}
