package com.saltatorv.file.storage.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class MultipleFilesResultTest extends FileResultTest {

    @Test
    public void canNotModifyResultValue() {
        // given
        produceSuccess();

        // when
        assertThrows(UnsupportedOperationException.class, this::modifyResultValue);

        // then
    }


    void produceSuccess() {
        fileResult = MultipleFilesResult.produceSuccess(List.of(file));
    }

    void produceFailure(String failureCause) {
        fileResult = MultipleFilesResult.produceFailure(failureCause);
    }

    @Override
    void assertResultValueIsValid() {
        assertEquals(List.of(file), fileResult.getValue());
    }

    private void modifyResultValue() {
        List<File> files = (List<File>) fileResult.getValue();
        files.add(file);
    }
}
