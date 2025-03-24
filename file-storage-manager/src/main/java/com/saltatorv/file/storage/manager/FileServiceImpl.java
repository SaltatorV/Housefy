package com.saltatorv.file.storage.manager;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;
import com.saltatorv.file.storage.manager.exception.FileStorageBaseException;
import com.saltatorv.file.storage.manager.validation.FileValidationRule;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileValidationRule validationRule;

    @Override
    public MultipleFilesResult getFiles(Path directoryPath) {
        MultipleFilesResult multipleFilesResult;
        try (Stream<Path> filePaths = Files.list(directoryPath)) {
            multipleFilesResult = MultipleFilesResult.produceSuccess(filePaths.map(File::new).toList());
        } catch (IOException e) {
            multipleFilesResult = MultipleFilesResult.produceFailure("Can not get files from: %s".formatted(directoryPath));
        }

        return multipleFilesResult;
    }

    @Override
    public SingleFileResult getFile(Path filePath) {
        SingleFileResult singleFileResult;
        try {
            File file = new File(filePath);
            singleFileResult = SingleFileResult.produceSuccess(file);
        } catch (FileStorageBaseException ex) {
            singleFileResult = SingleFileResult.produceFailure(ex.getMessage());
        }
        return singleFileResult;
    }

    @Override
    public SingleFileResult uploadFile(UploadFileDto uploadFileDto) {
        return File.upload(uploadFileDto, validationRule);
    }
}
