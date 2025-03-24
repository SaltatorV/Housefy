package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;
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
import java.util.ArrayList;
import java.util.List;

import static com.saltatorv.file.storage.manager.dto.UploadFileDtoAssembler.buildUploadFileDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {
    @TempDir
    private Path temporaryDirectory;
    private FileService fileService;

    private FileResult fileResult;

    @BeforeEach
    public void setup() {
        fileService = new FileServiceImpl(mock(FileValidationRule.class));
    }

    @Test
    @DisplayName("Can upload new file")
    public void canUploadNewFile() {
        //given
        var fileName = "test.txt";
        var dto = createUploadFileDto(fileName);

        //when
        uploadFile(dto);

        //then
        assertSingleFileResultIsSuccess();
    }

    @Test
    @DisplayName("Can get file from destination")
    public void canGetFileFromDestination() {
        //given
        var fileName = "test.txt";
        createTestTextFile(fileName);

        //when
        getFile(fileName);

        //then
        assertSingleFileResultIsSuccess();
    }

    @Test
    @DisplayName("Should produce failure when FileStorageBaseException occurs when get file from destination")
    public void shouldProduceFailureWhenFileStorageBaseExceptionOccursWhenGetFileFromDestination() {
        //given
        var fileName = temporaryDirectory.resolve("test.txt");

        // when
        getFile(fileName);

        //then
        assertSingleFileResultIsFailure("File: %s do not exists.".formatted(fileName));
    }


    @Test
    @DisplayName("Can get list of files from specific directory")
    public void canGetListOfFilesFromSpecificDirectory() {
        //given
        var expectedFiles = createTenTextFiles();

        //when
        getFiles();

        //then
        assertMultipleFileResultIsSuccess(expectedFiles);
    }

    @Test
    @DisplayName("Should produce failure when IOException occurs when get files from destination")
    public void shouldProduceFailureWhenIOExceptionOccursWhenGetFilesFromDestination() {
        //given

        try (MockedStatic<Files> filesSystem = Mockito.mockStatic(Files.class)) {
            filesSystem.when(() -> Files.list(temporaryDirectory))
                    .thenThrow(new IOException());

            // when
            getFiles();
        }


        //then
        assertSingleFileResultIsFailure("Can not get files from: %s".formatted(temporaryDirectory));
    }

    private void uploadFile(UploadFileDto dto) {
        fileResult = fileService.uploadFile(dto);
    }

    private void getFile(String fileName) {
        fileResult = fileService.getFile(temporaryDirectory.resolve(fileName));
    }

    private void getFile(Path fileName) {
        getFile(fileName.toString());
    }

    private void getFiles() {
        fileResult = fileService.getFiles(temporaryDirectory);
    }

    private File createTestTextFile(String fileName) {
        UploadFileDto dto = createUploadFileDto(fileName);
        return File.upload(dto, mock(FileValidationRule.class)).getValue();
    }

    private UploadFileDto createUploadFileDto(String fileName) {
        return buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(temporaryDirectory.resolve(fileName))
                .create();
    }


    private List<File> createTenTextFiles() {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            File file = createTestTextFile(String.join("", "test", String.valueOf(i), ".txt"));
            files.add(file);
        }
        return files;
    }


    private void assertSingleFileResultIsSuccess() {
        assertTrue(fileResult.isSuccess());
        assertNotNull(fileResult.getValue());
    }

    private void assertMultipleFileResultIsSuccess(List<File> expectedFiles) {
        assertTrue(fileResult.isSuccess());
        assertEquals(expectedFiles, fileResult.getValue());
    }

    private void assertSingleFileResultIsFailure(String expectedFailureCause) {
        assertFalse(fileResult.isSuccess());
        assertNull(fileResult.getValue());
        assertEquals(expectedFailureCause, fileResult.getFailureCause());
    }

    private void assertMultipleFileResultIsFail(String expectedFailureCause) {
        assertFalse(fileResult.isSuccess());
        assertNull(fileResult.getValue());
        assertEquals(expectedFailureCause, fileResult.getFailureCause());
    }
}
