package com.saltatorv.housefy.file.storage.manager;

import com.saltatorv.housefy.file.storage.manager.dto.UploadFileData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.saltatorv.housefy.file.storage.manager.UploadFileDataBuilder.buildUploadFileData;
import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    private static final String TEST_DESTINATION = "test-destination";

    @BeforeEach
    void setup() {
        try {
            Path path = Path.of(TEST_DESTINATION);
            deleteDirectory(new java.io.File(path.toString()));
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateFile() {
        //given
        var data = buildUploadFileData().withFileName("test.txt").withDestination(TEST_DESTINATION).withContent("test-content".getBytes()).create();

        //when
        createFile(data);

        //then
        assertFileExists("test-destination/test.txt");
        assertFileContain("test-destination/test.txt", "test-content");
    }

    @Test
    void testRaiseFileAlreadyExistsExceptionWhenFileAlreadyCreated() {
        //given
        var data = buildUploadFileData().withFileName("test.txt").withDestination(TEST_DESTINATION).withContent("test-content".getBytes()).create();

        //when
        createFile(data);
        assertThrows(FileAlreadyExistsException.class, () -> createFile(data));

        //then
    }

    private void createFile(UploadFileData data) {
        File file = File.initialize(data.getDestination(), data.getFileName());
        file.create(data.getContent());
    }

    private void assertFileExists(String path) {
        assertTrue(Files.exists(Path.of(path)));
    }

    private void assertFileContain(String path, String expectedContent) {
        try {
            byte[] content = Files.readAllBytes(Path.of(path));
            assertArrayEquals(expectedContent.getBytes(), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteDirectory(java.io.File directory) {
        if (directory.exists() && directory.isDirectory()) {
            java.io.File[] files = directory.listFiles();
            if (files != null) {
                for (java.io.File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        directory.delete();
    }

}
