package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.saltatorv.file.storage.manager.command.UploadFileCommandAssembler.buildUploadFileCommand;
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
        assertThrows(RuntimeException.class, () -> createValidationRule(contentTypes));

        //then
    }

    @Test
    @DisplayName("Can successfully validate upload file command")
    public void canSuccessfullyValidateUploadFileCommand() {
        //given
        var command = buildUploadFileCommand()
                .uploadTextFileAsDefault()
                .butWithContentType("plain/text")
                .create();

        var contentTypes = buildContentTypes()
                .addContentType("plain/text")
                .create();

        createValidationRule(contentTypes);

        //when
        assertDoesNotThrow(() -> validateCommand(command));

        //then

    }

    @Test
    @DisplayName("Can successfully validate upload file command when rule contain multiple content types and only one is valid")
    public void canSuccessfullyValidateUploadFileCommandWhenRuleContainMultipleContentTypesAndOnlyOneIsValid() {
        //given
        var command = buildUploadFileCommand()
                .uploadTextFileAsDefault()
                .butWithContentType("plain/text")
                .create();

        var contentTypes = buildContentTypes()
                .addContentType("img/png")
                .addContentType("plain/text")
                .create();

        createValidationRule(contentTypes);

        //when
        assertDoesNotThrow(() -> validateCommand(command));

        //then

    }

    @Test
    @DisplayName("Can throw exception when validation fail")
    public void canThrowExceptionWhenValidationFail() {
        //given
        var command = buildUploadFileCommand()
                .uploadTextFileAsDefault()
                .butWithContentType("plain/text")
                .create();

        var contentTypes = buildContentTypes()
                .addContentType("img/png")
                .create();

        createValidationRule(contentTypes);

        //when
        assertThrows(RuntimeException.class, () -> validateCommand(command));

        //then
    }

    private void createValidationRule(Set<String> contentTypes) {
        validationRule = new FileContentTypeValidationRule(contentTypes);
    }

    private void validateCommand(UploadFileCommand command) {
        validationRule.validate(command);
    }
}
