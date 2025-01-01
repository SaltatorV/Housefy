package com.saltatorv.file.storage.manager;

import org.junit.jupiter.api.BeforeEach;

public class FileServiceImplTest {

    FileService fileService;

    @BeforeEach
    public void setup() {
        fileService = new FileServiceImpl();
    }
}
