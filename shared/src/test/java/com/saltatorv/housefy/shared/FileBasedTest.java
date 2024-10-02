package com.saltatorv.housefy.shared;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public abstract class FileBasedTest {
    private Path testDirectoryPath;

    public FileBasedTest(String testDirectoryPath) {
        this.testDirectoryPath = Path.of(testDirectoryPath);
    }

    public abstract void createFile();

    @BeforeEach
    public void setup() {
        removeTestFiles();
    }

    @AfterEach
    public void clean() {
        removeTestFiles();
    }

    private void removeTestFiles() {
        try (Stream<Path> paths = Files.walk(this.testDirectoryPath)) {
            paths.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
