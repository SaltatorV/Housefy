package com.saltatorv.housefy.file.storage.manager;

import com.saltatorv.housefy.file.storage.manager.dto.UploadFileData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.saltatorv.housefy.file.storage.manager.UploadFileDataBuilder.buildUploadFileData;
import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    private static final Path TEST_DESTINATION = Path.of("test-destination");

    @BeforeEach
    void setup() {
        deleteDirectory(new java.io.File(TEST_DESTINATION.toString()));

        try {
            Files.createDirectories(TEST_DESTINATION);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void clear() {
        deleteDirectory(new java.io.File(TEST_DESTINATION.toString()));
    }

    @Test
    void testCreateFile() {
        //given
        var data = buildUploadFileData()
                .withFileName("test.txt")
                .withDestination(TEST_DESTINATION)
                .withContent("test-content".getBytes())
                .create();

        //when
        createFile(data);

        //then
        assertFileExistsInTestDestination("test.txt");
        assertFileInTestDestinationContain("test.txt", "test-content");
    }

    @Test
    void testRaiseFileAlreadyExistsExceptionWhenFileAlreadyCreated() {
        //given
        var data = buildUploadFileData()
                .withFileName("test.txt")
                .withDestination(TEST_DESTINATION)
                .withContent("test-content".getBytes())
                .create();

        //when
        createFile(data);
        assertThrows(FileAlreadyExistsException.class, () -> createFile(data));

        //then
    }

    private void createFile(UploadFileData data) {
        File file = File.initialize(data.getDestination(), data.getFileName());
        file.create(data.getContent());
    }

    private void assertFileExistsInTestDestination(String fileName) {

        assertTrue(Files.exists(setupTestDestinationPath(fileName)));
    }

    private void assertFileInTestDestinationContain(String fileName, String expectedContent) {
        try {
            byte[] content = Files.readAllBytes(setupTestDestinationPath(fileName));
            assertArrayEquals(expectedContent.getBytes(), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path setupTestDestinationPath(String fileName) {
        return TEST_DESTINATION.resolve(fileName);
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
