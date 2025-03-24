package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;
import com.saltatorv.file.storage.manager.exception.FileNotFoundException;
import com.saltatorv.file.storage.manager.exception.FileStorageBaseException;
import com.saltatorv.file.storage.manager.exception.NotRegularFileException;
import com.saltatorv.file.storage.manager.validation.FileValidationRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.saltatorv.file.storage.manager.dto.UploadFileDtoAssembler.buildUploadFileDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileTest {

    @TempDir
    private Path temporaryDirectory;
    private File file;
    private SingleFileResult fileResult;

    @BeforeEach
    public void setup() {
        fileResult = null;
    }

    @Test
    @DisplayName("Can upload new file")
    public void canUploadNewFile() {
        //given
        var fileName = temporaryDirectory.resolve("test.txt");

        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(fileName)
                .create();

        var validationRule = createDummyValidationRule();

        //when
        uploadFile(dto, validationRule);

        //then
        assertFileResultIsSuccess();
    }

    @Test
    @DisplayName("Can upload new file when directories not exists")
    public void canUploadNewFileWhenDirectoriesNotExists() {
        //given
        var fileName = temporaryDirectory.resolve("tmp/test.txt");

        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(fileName)
                .create();

        var validationRule = createDummyValidationRule();

        //when
        uploadFile(dto, validationRule);

        //then
        assertFileExists(fileName);
        assertFileContain(fileName, dto.getContent());
        assertFileResultIsSuccess();
    }

    @Test
    @DisplayName("Can not upload file when validation rule fail")
    public void canNotUploadFileWhenValidationRuleFail() {
        // given
        var fileName = temporaryDirectory.resolve("tmp/test.txt");

        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(fileName)
                .create();

        var message = "Validation failed";

        var validationRule = createFailingValidationRule(message);

        // when
        uploadFile(dto, validationRule);

        // then
        assertFileResultIsFail(message);
    }


    @Test
    @DisplayName("Can create file object when file exists")
    public void canCreateFileObjectWhenFileExists() {
        //given
        var fileName = temporaryDirectory.resolve("test.txt");

        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(fileName)
                .create();

        uploadFile(dto, createDummyValidationRule());

        //when
        createFile(dto.getFileName());

        //then
        assertFileObjectIsNotNull();
    }


    @Test
    @DisplayName("Should produce failure when IOException occurs during creating directories")
    public void shouldProduceFailureWhenIOExceptionOccursDuringCreatingDirectories() {
        // given
        var fileName = temporaryDirectory.resolve("test.txt");

        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(fileName)
                .create();

        var validationRule = createDummyValidationRule();

        try (MockedStatic<Files> filesSystem = Mockito.mockStatic(Files.class)) {
            filesSystem.when(() -> Files.createDirectories(temporaryDirectory))
                    .thenThrow(new IOException());

            // when
            uploadFile(dto, validationRule);
        }

        // then
        assertFileResultIsFail("Can not write to file: %s".formatted(fileName));
    }

    @Test
    @DisplayName("Should produce failure when IOException occurs during write to file")
    public void shouldProduceFailureWhenIOExceptionOccursDuringWriteToFile() {
        // given
        var fileName = temporaryDirectory.resolve("test.txt");

        var fileContent = "Test content";

        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(fileName)
                .butWithContent(fileContent)
                .create();

        var validationRule = createDummyValidationRule();

        try (MockedStatic<Files> filesSystem = Mockito.mockStatic(Files.class)) {
            filesSystem.when(() -> Files.write(fileName, fileContent.getBytes()))
                    .thenThrow(new IOException());

            // when
            uploadFile(dto, validationRule);
        }

        // then
        assertFileResultIsFail("Can not write to file: %s".formatted(fileName));
    }

    @Test
    @DisplayName("Can not create file object when file not exists")
    public void canNotCreateFileObjectWhenFileNotExists() {
        //given
        var fileName = temporaryDirectory.resolve("test.txt");

        //when
        assertThrows(FileNotFoundException.class, () -> createFile(fileName));

        //then
    }

    @Test
    @DisplayName("Can not create file object when destination point to directory")
    public void canNotCreateFileObjectWhenDestinationPointToDirectory() {
        //given

        //when
        assertThrows(NotRegularFileException.class, () -> createFile(temporaryDirectory));

        //then
    }

    @Test
    @DisplayName("Can delete file")
    public void canDeleteFile() {
        //given
        var fileName = temporaryDirectory.resolve("test.txt");

        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(fileName)
                .create();

        uploadFile(dto, createDummyValidationRule());

        //when
        var deleteResult = deleteFile();

        //then
        assertTrue(deleteResult);
        assertFileNotExists(fileName);
    }

    @Test
    @DisplayName("Can delete even if file do not exists")
    public void canDeleteEvenIfFileDoNotExists() {
        //given
        var fileName = temporaryDirectory.resolve("test.txt");

        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(fileName)
                .create();

        uploadFile(dto, createDummyValidationRule());
        deleteFile();

        //when
        var deleteResult = deleteFile();

        //then
        assertFalse(deleteResult);
        assertFileNotExists(fileName);
    }

    @Test
    @DisplayName("Can read from file using proper file content reader")
    public void canReadFromFileUsingProperFileContentReader() {
        //given
        var fileName = temporaryDirectory.resolve("test.txt");

        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(fileName)
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

    private FileValidationRule createFailingValidationRule(String message) {
        var rule = mock(FileValidationRule.class);
        doThrow(new FileStorageBaseException(message)).when(rule).validate(any());
        return rule;
    }

    private void uploadFile(UploadFileDto dto, FileValidationRule validationRule) {
        fileResult = File.upload(dto, validationRule);
    }

    private void createFile(Path fileName) {
        file = new File(fileName);
    }

    private boolean deleteFile() {
        return fileResult.getValue().delete();
    }

    private String readFromFile(FileContentReader<String> reader) {
        return fileResult.getValue().read(reader);
    }

    private void assertFileExists(Path fileDestination) {
        assertTrue(Files.exists(fileDestination));
    }

    private void assertFileNotExists(Path fileDestination) {
        assertFalse(Files.exists(fileDestination));
    }

    private void assertFileContain(Path fileDestination, byte[] expectedContent) {
        String fileContent;
        try {
            fileContent = Files.readString(fileDestination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(new String(expectedContent), fileContent);
    }

    private void assertFileResultIsSuccess() {
        assertTrue(fileResult.isSuccess());
        assertNotNull(fileResult.getValue());
    }

    private void assertFileResultIsFail(String expectedFailureCause) {
        assertFalse(fileResult.isSuccess());
        assertNull(fileResult.getValue());
        assertEquals(expectedFailureCause, fileResult.getFailureCause());
    }

    private void assertFileObjectIsNotNull() {
        assertNotNull(file);
    }
}
