package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import com.saltatorv.file.storage.manager.validation.FileValidationRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static com.saltatorv.file.storage.manager.command.UploadFileCommandAssembler.buildUploadFileCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private File getFile(String fileName) {
        return fileService.getFile(TEST_DIRECTORY.resolve(fileName));
    }

    private File createTestTextFile(String fileName) {
        UploadFileCommand command = buildUploadFileCommand()
                .uploadTextFileAsDefault()
                .butWithFileName(TEST_DIRECTORY.resolve(fileName))
                .create();

        return File.upload(command, mock(FileValidationRule.class));
    }
}
