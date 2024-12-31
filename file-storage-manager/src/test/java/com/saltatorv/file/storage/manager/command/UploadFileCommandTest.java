package com.saltatorv.file.storage.manager.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.saltatorv.file.storage.manager.command.UploadFileCommandObjectMother.uploadTextFileCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UploadFileCommandTest {

    @Test
    @DisplayName("Can get content copy")
    public void canGetContentCopy() {
        //given
        var command = uploadTextFileCommand();

        //when
        var content = command.getContent();

        //then
        assertContentsAreEqual(content, command.getContent());
    }

    @Test
    @DisplayName("Can not modify content")
    public void canNotModifyContent() {
        //given
        var command = uploadTextFileCommand();
        //when
        var content = command.getContent();
        modifyFirstElementOfArray(content);

        //then
        assertContentsAreNotEqual(content, command.getContent());
    }

    private void modifyFirstElementOfArray(byte[] content) {
        content[0] = 1;
    }

    private void assertContentsAreEqual(byte[] first, byte[] second) {
        assertEquals(new String(first), new String(second));
    }

    private void assertContentsAreNotEqual(byte[] first, byte[] second) {
        assertNotEquals(new String(first), new String(second));
    }
}
