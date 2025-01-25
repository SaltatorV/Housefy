package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;
import com.saltatorv.file.storage.manager.validation.FileValidationRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.saltatorv.file.storage.manager.dto.UploadFileDtoAssembler.buildUploadFileDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest extends FilesBasedTest {
    private static final Path TEST_DIRECTORY = Path.of("tmp");

    private FileService fileService;

    public FileServiceImplTest() {
        super(TEST_DIRECTORY);
    }

    @BeforeEach
    public void setup() {
        fileService = new FileServiceImpl(mock(FileValidationRule.class));
    }

    @Test
    @DisplayName("Can get file from destination")
    public void canGetFileFromDestination() {
        //given
        var fileName = "test.txt";
        var expectedFile = createTestTextFile(fileName);

        //when
        var actualFile = getFile(fileName);

        //then
        assertEquals(expectedFile, actualFile);
    }

    @Test
    @DisplayName("Can upload new file")
    public void canUploadNewFile() {
        //given
        var fileName = "test.txt";
        var dto = createUploadFileCommand(fileName);

        //when
        var uploadedFile = uploadFile(dto);


        //then
        assertFileExists(fileName);
        assertEquals(uploadedFile, createFile(fileName));
    }

    @Test
    @DisplayName("Can get list of files from specific directory")
    public void canGetListOfFilesFromSpecificDirectory() {
        //given
        var expectedFiles = createTenTextFiles();

        //when
        var actualFiles = getFiles();

        //then
        assertEquals(expectedFiles, actualFiles);
    }


    private File uploadFile(UploadFileDto dto) {
        return fileService.uploadFile(dto);
    }


    private File getFile(String fileName) {
        return fileService.getFile(TEST_DIRECTORY.resolve(fileName));
    }

    private List<File> getFiles() {
        return fileService.getFiles(TEST_DIRECTORY);
    }

    private File createTestTextFile(String fileName) {
        UploadFileDto dto = createUploadFileCommand(fileName);

        return File.upload(dto, mock(FileValidationRule.class));
    }

    private UploadFileDto createUploadFileCommand(String fileName) {
        return buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithFileName(TEST_DIRECTORY.resolve(fileName))
                .create();
    }

    private File createFile(String fileName) {
        return new File(TEST_DIRECTORY.resolve(fileName));
    }

    private List<File> createTenTextFiles() {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            File file = createTestTextFile(String.join("", "test", String.valueOf(i), ".txt"));
            files.add(file);
        }
        return files;
    }

    private void assertFileExists(String fileDestination) {
        assertTrue(Files.exists(TEST_DIRECTORY.resolve(fileDestination)));
    }
}
