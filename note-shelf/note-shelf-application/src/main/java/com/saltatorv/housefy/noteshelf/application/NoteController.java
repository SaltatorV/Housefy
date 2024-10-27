package com.saltatorv.housefy.noteshelf.application;

import com.saltatorv.housefy.noteshelf.service.NoteService;
import com.saltatorv.housefy.noteshelf.service.UploadNoteCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/note-shelf/note/upload")
    public void uploadNewNote(UploadNoteCommand command) {
        noteService.uploadNewNote(command);
    }
}
