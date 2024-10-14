package com.saltator.housefy.noteshelf;

import com.saltatorv.housefy.file.storage.manager.FileManager;
import com.saltatorv.housefy.file.storage.manager.dto.UploadFileData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final FileManager fileManager;

    @PostMapping("/note-shelf/note/upload")
    public void uploadFile(MultipartFile file) {
        try {
            UploadFileData uploadFileData = new UploadFileData(file.getName(), "notes", file.getBytes());
            fileManager.uploadFile(uploadFileData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
