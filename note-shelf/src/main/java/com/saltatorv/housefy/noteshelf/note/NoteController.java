package com.saltatorv.housefy.noteshelf.note;

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
