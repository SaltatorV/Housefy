package com.saltatorv.file.storage.manager.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.saltatorv.file.storage.manager.dto.UploadFileDtoObjectMother.uploadTextFileDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UploadFileDtoTest {

    @Test
    @DisplayName("Can get content copy")
    public void canGetContentCopy() {
        //given
        var dto = uploadTextFileDto();

        //when
        var content = dto.getContent();

        //then
        assertContentsAreEqual(content, dto.getContent());
    }

    @Test
    @DisplayName("Can not modify content")
    public void canNotModifyContent() {
        //given
        var dto = uploadTextFileDto();
        //when
        var content = dto.getContent();
        modifyFirstElementOfArray(content);

        //then
        assertContentsAreNotEqual(content, dto.getContent());
    }

    private void modifyFirstElementOfArray(byte[] content) {
        content[0] = 1;
    }

    private void assertContentsAreEqual(byte[] first, byte[] second) {
        assertEquals(new String(first), new String(second));
    }

    private void assertContentsAreNotEqual(byte[] first, byte[] second) {
        assertNotEquals(new String(first), new String(second));
    }
}
