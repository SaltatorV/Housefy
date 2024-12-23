package com.saltatorv.file.storage.manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileTest extends FilesBasedTest {

    private static final Path TEST_DIRECTORY = Path.of("tmp");
    private File resultFile;

    public FileTest() {
        super(TEST_DIRECTORY);
    }

    @Test
    @DisplayName("Can upload new file")
    public void canUploadNewFile() {
        //given
        var createDirectories = true;
        var content = "Test content";
        var fileDestination = "test";
        var fileName = "test.txt";

        //when
        uploadFile(fileName, fileDestination, content, createDirectories);


        //then
        assertFileExists("tmp/test/test.txt");
        assertFileContain("tmp/test/test.txt", content);
    }

    @Test
    @DisplayName("Can not upload new file when directories not exists and createDirectories flag is false")
    public void canNotUploadNewFileWhenDirectoriesNotExistsAndCreateDirectoriesFlagIsFalse() {
        //given
        var createDirectories = false;
        var content = "Test content";
        var fileDestination = "test";
        var fileName = "test.txt";

        //when
        assertThrows(RuntimeException.class, () -> uploadFile(fileName, fileDestination, content, createDirectories));


        //then
        assertFileNotExists("tmp/test/test.txt");
    }

    @Test
    @DisplayName("Can create file object when file exists")
    public void canCreateFileWhenFileExists() {
        //given
        var createDirectories = true;
        var content = "Test content";
        var fileDestination = "test";
        var fileName = "test.txt";
        uploadFile(fileName, fileDestination, content, createDirectories);

        //when
        createFile(fileDestination);

        //then
        assertFileExists("tmp/test/test.txt");
        assertFileContain("tmp/test/test.txt", content);
    }

    @Test
    @DisplayName("Can not create file object when file not exists")
    public void canNotCreateFileObjectWhenFileNotExists() {
        //given
        var fileDestination = "test";

        //when
        assertThrows(RuntimeException.class, () -> createFile(fileDestination));

        //then
    }

    private void uploadFile(String fileName, String fileDestination, String content, boolean createDirectories) {
        File.upload(fileName, TEST_DIRECTORY.resolve(fileDestination).toString(), content, createDirectories);
    }

    private void createFile(String fileDestination) {
        resultFile = new File(TEST_DIRECTORY.resolve(fileDestination));
    }

    private void assertFileExists(String fileDestination) {
        assertTrue(Files.exists(Path.of(fileDestination)));
    }

    private void assertFileNotExists(String fileDestination) {
        assertFalse(Files.exists(Path.of(fileDestination)));
    }

    private void assertFileContain(String fileDestination, String expectedContent) {
        String fileContent;
        try {
            fileContent = Files.readString(Path.of(fileDestination));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedContent, fileContent);
    }
}
