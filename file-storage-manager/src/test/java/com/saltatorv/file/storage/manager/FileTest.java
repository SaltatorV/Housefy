package com.saltatorv.file.storage.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.jupiter.api.Assertions.*;

public class FileTest {

    public static void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }

    }

    @BeforeEach
    @AfterEach
    public void setup() {
        try {
            deleteDirectoryRecursively(Path.of("tmp"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Can upload new file")
    public void canUploadNewFile() {
        //given
        var createDirectories = true;
        var content = "Test content";
        var fileDestination = "tmp";
        var fileName = "test.txt";

        //when
        File.upload(fileName, fileDestination, content, createDirectories);

        //then
        assertFileExists("tmp/test.txt");
        assertFileContain("tmp/test.txt", content);
    }

    @Test
    @DisplayName("Can not upload new file when directories not exists and createDirectories flag is false")
    public void canNotUploadNewFileWhenDirectoriesNotExistsAndCreateDirectoriesFlagIsFalse() {
        //given
        var createDirectories = false;
        var content = "Test content";
        var fileDestination = "tmp";
        var fileName = "test.txt";

        //when
        assertThrows(RuntimeException.class, () -> File.upload(fileName, fileDestination, content, createDirectories));


        //then
        assertFileNotExists("tmp/test.txt");
    }

    private void assertFileExists(String fileDestination) {
        assertTrue(Files.exists(Path.of(fileDestination)));
    }

    private void assertFileNotExists(String fileDestination) {
        assertFalse(Files.exists(Path.of(fileDestination)));
    }

    private void assertFileContain(String fileDestination, String expectedContent) {
        String fileContent = null;
        try {
            fileContent = Files.readString(Path.of(fileDestination));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedContent, fileContent);
    }
}
