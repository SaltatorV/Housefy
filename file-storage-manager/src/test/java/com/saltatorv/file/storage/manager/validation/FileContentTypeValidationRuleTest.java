package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;
import com.saltatorv.file.storage.manager.exception.FileContainInvalidContentType;
import com.saltatorv.file.storage.manager.exception.FileContentTypeValidationRuleSetCanNotBeEmpty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.saltatorv.file.storage.manager.dto.UploadFileDtoAssembler.buildUploadFileDto;
import static com.saltatorv.file.storage.manager.validation.ContentTypeSetAssembler.buildContentTypes;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FileContentTypeValidationRuleTest {

    private FileContentTypeValidationRule validationRule;

    @BeforeEach
    public void setup() {
        validationRule = null;
    }

    @Test
    @DisplayName("Can throw exception when create rule with empty set of content types")
    public void canThrowExceptionWhenCreateRuleWithEmptySetOfContentTypes() {
        //given
        var contentTypes = buildContentTypes()
                .create();

        //when
        assertThrows(FileContentTypeValidationRuleSetCanNotBeEmpty.class, () -> createValidationRule(contentTypes));

        //then
    }

    @Test
    @DisplayName("Can successfully validate upload file dto")
    public void canSuccessfullyValidateUploadFileCommand() {
        //given
        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithContentType("plain/text")
                .create();

        var contentTypes = buildContentTypes()
                .addContentType("plain/text")
                .create();

        createValidationRule(contentTypes);

        //when
        assertDoesNotThrow(() -> validateCommand(dto));

        //then

    }

    @Test
    @DisplayName("Can successfully validate upload file dto when rule contain multiple content types and only one is valid")
    public void canSuccessfullyValidateUploadFileCommandWhenRuleContainMultipleContentTypesAndOnlyOneIsValid() {
        //given
        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithContentType("plain/text")
                .create();

        var contentTypes = buildContentTypes()
                .addContentType("img/png")
                .addContentType("plain/text")
                .create();

        createValidationRule(contentTypes);

        //when
        assertDoesNotThrow(() -> validateCommand(dto));

        //then

    }

    @Test
    @DisplayName("Can throw exception when validation fail")
    public void canThrowExceptionWhenValidationFail() {
        //given
        var dto = buildUploadFileDto()
                .uploadTextFileAsDefault()
                .butWithContentType("plain/text")
                .create();

        var contentTypes = buildContentTypes()
                .addContentType("img/png")
                .create();

        createValidationRule(contentTypes);

        //when
        assertThrows(FileContainInvalidContentType.class, () -> validateCommand(dto));

        //then
    }

    private void createValidationRule(Set<String> contentTypes) {
        validationRule = new FileContentTypeValidationRule(contentTypes);
    }

    private void validateCommand(UploadFileDto dto) {
        validationRule.validate(dto);
    }
}
