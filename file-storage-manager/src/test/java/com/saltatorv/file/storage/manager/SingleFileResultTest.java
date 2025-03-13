package com.saltatorv.file.storage.manager;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SingleFileResultTest extends FileResultTest {

    void produceSuccess() {
        fileResult = SingleFileResult.produceSuccess(file);
    }

    void produceFailure(String failureCause) {
        fileResult = SingleFileResult.produceFailure(failureCause);
    }

    @Override
    void assertResultValueIsValid() {
        assertEquals(file, fileResult.getValue());
    }

}
