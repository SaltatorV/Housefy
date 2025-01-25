package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;
import com.saltatorv.file.storage.manager.exception.DirectoryUnavailableException;
import com.saltatorv.file.storage.manager.exception.FileNotFoundException;
import com.saltatorv.file.storage.manager.exception.NotRegularFileException;
import com.saltatorv.file.storage.manager.validation.FileValidationRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.saltatorv.file.storage.manager.dto.UploadFileDtoAssembler.buildUploadFileDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileTest extends FilesBasedTest {

    private static final Path TEST_DIRECTORY = Path.of("tmp");
    private File resultFile;

    public FileTest() {
        super(TEST_DIRECTORY);
    }

    @BeforeEach
    public void setup() {
        resultFile = null;
    }

    @Test
    @DisplayName("Can upload new file")
    public void canUploadNewFile() {
        //given
        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(TEST_DIRECTORY.resolve("test.txt"))
                .create();

        var validationRule = createDummyValidationRule();

        //when
        uploadFile(dto, validationRule);

        //then
        assertValidationRuleWasCalledOnce(validationRule, dto);
        assertFileExists("tmp/test.txt");
        assertFileContain("tmp/test.txt", dto.getContent());
    }

    @Test
    @DisplayName("Can not upload new file when directories not exists and createDirectories flag is false")
    public void canNotUploadNewFileWhenDirectoriesNotExistsAndCreateDirectoriesFlagIsFalse() {
        //given
        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(TEST_DIRECTORY.resolve("test.txt"))
                .skipDirectoryCreation()
                .create();

        var validationRule = createDummyValidationRule();

        //when
        assertThrows(DirectoryUnavailableException.class, () -> uploadFile(dto, validationRule));

        //then
        assertValidationRuleWasCalledOnce(validationRule, dto);
        assertFileNotExists("tmp/test/test.txt");
    }

    @Test
    @DisplayName("Can upload new file when directories exists and createDirectories flag is false")
    public void canUploadNewFileWhenDirectoriesExistsAndCreateDirectoriesFlagIsFalse() {
        //given
        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(TEST_DIRECTORY.resolve("test.txt"))
                .skipDirectoryCreation()
                .create();
        var validationRule = createDummyValidationRule();
        createTestDirectory();

        //when
        uploadFile(dto, validationRule);

        //then
        assertValidationRuleWasCalledOnce(validationRule, dto);
        assertFileExists("tmp/test.txt");
        assertFileContain("tmp/test.txt", dto.getContent());
    }

    @Test
    @DisplayName("Can create file object when file exists")
    public void canCreateFileObjectWhenFileExists() {
        //given
        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(TEST_DIRECTORY.resolve("test.txt"))
                .create();

        uploadFile(dto, createDummyValidationRule());

        //when
        createFile(dto.getFileName());

        //then
        assertFileExists("tmp/test.txt");
        assertFileContain("tmp/test.txt", dto.getContent());
    }

    @Test
    @DisplayName("Can not create file object when file not exists")
    public void canNotCreateFileObjectWhenFileNotExists() {
        //given
        var destination = Path.of("test.txt");

        //when
        assertThrows(FileNotFoundException.class, () -> createFile(destination));

        //then
    }

    @Test
    @DisplayName("Can not create file object when destination point to directory")
    public void canNotCreateFileObjectWhenDestinationPointToDirectory() {
        //given
        createTestDirectory();

        //when
        assertThrows(NotRegularFileException.class, () -> createFile(TEST_DIRECTORY));

        //then
    }

    @Test
    @DisplayName("Can delete file")
    public void canDeleteFile() {
        //given
        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(TEST_DIRECTORY.resolve("test.txt"))
                .create();

        uploadFile(dto, createDummyValidationRule());

        //when
        var deleteResult = deleteFile();

        //then
        assertTrue(deleteResult);
        assertFileNotExists("tmp/test/test.txt");
    }

    @Test
    @DisplayName("Can delete even if file do not exists")
    public void canDeleteEvenIfFileDoNotExists() {
        //given
        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(TEST_DIRECTORY.resolve("test.txt"))
                .create();

        uploadFile(dto, createDummyValidationRule());
        deleteFile();

        //when
        var deleteResult = deleteFile();

        //then
        assertFalse(deleteResult);
        assertFileNotExists("tmp/test.txt");
    }

    @Test
    @DisplayName("Can read from file using proper file content reader")
    public void canReadFromFileUsingProperFileContentReader() {
        //given
        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(TEST_DIRECTORY.resolve("test.txt"))
                .butWithContent("Test content")
                .create();

        uploadFile(dto, createDummyValidationRule());

        var reader = mock(FileContentReader.class);

        given(reader.read(dto.getFileName())).willReturn("Test content");

        //when
        var actualContent = readFromFile(reader);

        //then
        assertEquals("Test content", actualContent);
    }

    private FileValidationRule createDummyValidationRule() {
        return mock(FileValidationRule.class);
    }

    private void uploadFile(UploadFileDto dto, FileValidationRule validationRule) {
        resultFile = File.upload(dto, validationRule);
    }

    private void createFile(Path fileName) {
        resultFile = new File(fileName);
    }

    private boolean deleteFile() {
        return resultFile.delete();
    }

    private String readFromFile(FileContentReader<String> reader) {
        return resultFile.read(reader);
    }

    private void createTestDirectory() {
        try {
            Files.createDirectory(TEST_DIRECTORY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void assertValidationRuleWasCalledOnce(FileValidationRule rule, UploadFileDto dto) {
        then(rule)
                .should(times(1))
                .validate(dto);
    }
}
