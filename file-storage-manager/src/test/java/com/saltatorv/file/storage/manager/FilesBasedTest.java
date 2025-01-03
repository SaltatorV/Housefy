package com.saltatorv.file.storage.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

abstract class FilesBasedTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilesBasedTest.class);
    private final Path testDirectory;

    public FilesBasedTest(Path testDirectory) {
        this.testDirectory = testDirectory;
    }

    @BeforeEach
    @AfterEach
    void clearTestDirectory() {
        try {
            deleteDirectoryRecursively();
        } catch (IOException e) {
            LOGGER.error("Can not delete test directories: {} recursively.", testDirectory);
            throw new RuntimeException(e);
        }
    }

    private void deleteDirectoryRecursively() throws IOException {
        if (Files.exists(testDirectory)) {
            Files.walkFileTree(testDirectory, new SimpleFileVisitor<Path>() {
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
}
