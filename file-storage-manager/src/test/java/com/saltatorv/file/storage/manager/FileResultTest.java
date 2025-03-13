package com.saltatorv.file.storage.manager;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

public abstract class FileResultTest {
    FileResult fileResult;
    @Mock
    File file;

    @Test
    public void canProduceSuccess() {
        // given

        // when
        produceSuccess();

        // then
        assertResultIsValidSuccess();
    }

    @Test
    public void canProduceFailure() {
        // given

        // when
        produceFailure("Some error...");

        // then
        assertResultIsValidFailure("Some error...");
    }


    abstract void produceSuccess();

    abstract void produceFailure(String failureCause);

    private void assertResultIsValidSuccess() {
        assertTrue(fileResult.isSuccess());
        assertResultValueIsValid();
    }

    abstract void assertResultValueIsValid();

    private void assertResultIsValidFailure(String expectedFailureCause) {
        assertFalse(fileResult.isSuccess());
        assertEquals(expectedFailureCause, fileResult.getFailureCause());
    }
}
