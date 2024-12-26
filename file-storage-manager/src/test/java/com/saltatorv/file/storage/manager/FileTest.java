package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.VO.Destination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.saltatorv.file.storage.manager.UploadFileCommandBuilder.buildUploadFileCommand;
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
        var command = buildUploadFileCommand("test.txt")
                .withDestination(TEST_DIRECTORY.resolve("test").toString())
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        //when
        uploadFile(command);


        //then
        assertFileExists("tmp/test/test.txt");
        assertFileContain("tmp/test/test.txt", command.getContent());
    }

    @Test
    @DisplayName("Can not upload new file when directories not exists and createDirectories flag is false")
    public void canNotUploadNewFileWhenDirectoriesNotExistsAndCreateDirectoriesFlagIsFalse() {
        //given
        var command = buildUploadFileCommand("test.txt")
                .withDestination(TEST_DIRECTORY.resolve("test").toString())
                .withContent("Test content")
                .withCreateDirectories(false)
                .create();

        //when
        assertThrows(RuntimeException.class, () -> uploadFile(command));


        //then
        assertFileNotExists("tmp/test/test.txt");
    }

    @Test
    @DisplayName("Can create file object when file exists")
    public void canCreateFileObjectWhenFileExists() {
        //given
        var command = buildUploadFileCommand("test.txt")
                .withDestination(TEST_DIRECTORY.resolve("test").toString())
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        uploadFile(command);

        //when
        createFile(command.getFileName());

        //then
        assertFileExists("tmp/test/test.txt");
        assertFileContain("tmp/test/test.txt", command.getContent());
    }

    @Test
    @DisplayName("Can not create file object when file not exists")
    public void canNotCreateFileObjectWhenFileNotExists() {
        //given
        var destination = new Destination(TEST_DIRECTORY.resolve("test"));

        //when
        assertThrows(RuntimeException.class, () -> createFile(destination));

        //then
    }

    @Test
    @DisplayName("Can not create file object when destination do not point to regular file")
    public void canNotCreateFileObjectWhenDestinationDoNotPointToRegularFile() {
        //given
        var command = buildUploadFileCommand("test.txt")
                .withDestination(TEST_DIRECTORY.resolve("test").toString())
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        //when
        assertThrows(RuntimeException.class, () -> createFile(command.getFileName()));

        //then
        assertFileNotExists("tmp/test/test.txt");
    }

    @Test
    @DisplayName("Can delete file")
    public void canDeleteFile() {
        //given
        var command = buildUploadFileCommand("test.txt")
                .withDestination(TEST_DIRECTORY.resolve("test").toString())
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        uploadFile(command);

        //when
        boolean deleteResult = deleteFile();

        //then
        assertTrue(deleteResult);
        assertFileNotExists("tmp/test/test.txt");
    }

    @Test
    @DisplayName("Can delete even if file do not exists")
    public void canDeleteEvenIfFileDoNotExists() {
        //given
        var command = buildUploadFileCommand("test.txt")
                .withDestination(TEST_DIRECTORY.resolve("test").toString())
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        uploadFile(command);
        deleteFile();

        //when
        boolean deleteResult = deleteFile();

        //then
        assertFalse(deleteResult);
        assertFileNotExists("tmp/test/test.txt");
    }

    private void uploadFile(UploadFileCommand command) {
        resultFile = File.upload(command);
    }

    private void createFile(Destination fileDestination) {
        resultFile = new File(fileDestination);
    }

    private boolean deleteFile() {
        return resultFile.delete();
    }

    private void assertFileExists(String fileDestination) {
        assertTrue(Files.exists(Path.of(fileDestination)));
    }

    private void assertFileNotExists(String fileDestination) {
        assertFalse(Files.exists(Path.of(fileDestination)));
    }

    private void assertFileContain(String fileDestination, byte[] expectedContent) {
        String fileContent;
        try {
            fileContent = Files.readString(Path.of(fileDestination));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(new String(expectedContent), fileContent);
    }
}
