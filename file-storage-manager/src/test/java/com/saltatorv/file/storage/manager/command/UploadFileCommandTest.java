package com.saltatorv.file.storage.manager.command;

import com.saltatorv.file.storage.manager.vo.Destination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static com.saltatorv.file.storage.manager.command.UploadFileCommandAssembler.buildUploadFileCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UploadFileCommandTest {

    @Test
    @DisplayName("Can get content copy")
    public void canGetContentCopy() {
        //given
        var command = buildUploadFileCommand("test.txt")
                .withDestination("/tmp")
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        //when
        var content = command.getContent();


        //then
        assertContentsAreEqual(content, command.getContent());
    }

    @Test
    @DisplayName("Can not modify content")
    public void canNotModifyContent() {
        //given
        var command = buildUploadFileCommand("test.txt")
                .withDestination("/tmp")
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        //when
        var content = command.getContent();
        modifyFirstElementOfArray(content);


        //then
        assertContentsAreNotEqual(content, command.getContent());
    }

    @Test
    @DisplayName("Can get resolved file name")
    public void canGetResolvedFileName() {
        //given
        var command = buildUploadFileCommand("test.txt")
                .withDestination("/tmp")
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        //when
        var fileName = command.getFileName();

        //then
        asserFileNameIsEqualTo("/tmp/test.txt", fileName);
    }

    private void asserFileNameIsEqualTo(String expectedFileName, Destination actualFileName) {
        expectedFileName = expectedFileName.replace("/", java.io.File.separator);
        expectedFileName = expectedFileName.replace("\"", java.io.File.separator);
        assertEquals(Path.of(expectedFileName), actualFileName.getDestination());
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
