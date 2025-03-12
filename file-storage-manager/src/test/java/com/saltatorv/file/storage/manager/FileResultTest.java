package com.saltatorv.file.storage.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileResultTest {

    @TempDir
    private Path tempDir;
    private Path tempFile;
    private FileResult fileResult;

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
        fileResult = FileResult.produceSuccess(new File(tempFile));
    }

    private void createForFailure(String failureCause) {
        fileResult = FileResult.produceFailure(failureCause);
    }

    private void assertFileUploadResultIsSuccess() {
        assertTrue(fileResult.isSuccess());
    }

    private void assertFileUploadResultIsFailure() {
        assertFalse(fileResult.isSuccess());
    }

    private void assertFileExists() {
        assertNotNull(fileResult.getValue());
    }

    private void assertReasonIs(String expectedMessage) {
        assertEquals(expectedMessage, fileResult.getFailureCause());
    }
}
