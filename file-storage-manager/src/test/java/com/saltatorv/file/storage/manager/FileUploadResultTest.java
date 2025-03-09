package com.saltatorv.file.storage.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileUploadResultTest {

    @TempDir
    private Path tempDir;
    private Path tempFile;
    private FileUploadResult fileUploadResult;

    @Test
    public void shouldReturnFileUploadResultForSuccess() {
        // given
        createTempFile("test.txt");

        // when
        createForSuccess();

        // then
        assertFileUploadResultIsSuccess();
        assertFileExists();
    }

    @Test
    public void shouldReturnFileUploadResultForFailure() {
        // given

        // when
        createForFailure("Can not upload file");

        // then
        assertFileUploadResultIsFailure();
        assertReasonIs("Can not upload file");
    }


    private void createTempFile(String fileName) {
        try {
            tempFile = Files.createTempFile(tempDir.getFileName().toString(), fileName);
        } catch (IOException e) {
            throw new RuntimeException("Can not create temporary file: %s".formatted(fileName));
        }
    }

    private void createForSuccess() {
        fileUploadResult = FileUploadResult.produceSuccess(new File(tempFile));
    }

    private void createForFailure(String failureCause) {
        fileUploadResult = FileUploadResult.produceFailure(failureCause);
    }

    private void assertFileUploadResultIsSuccess() {
        assertTrue(fileUploadResult.isSuccess());
    }

    private void assertFileUploadResultIsFailure() {
        assertFalse(fileUploadResult.isSuccess());
    }

    private void assertFileExists() {
        assertNotNull(fileUploadResult.getValue());
    }

    private void assertReasonIs(String expectedMessage) {
        assertEquals(expectedMessage, fileUploadResult.getFailureCause());
    }
}
