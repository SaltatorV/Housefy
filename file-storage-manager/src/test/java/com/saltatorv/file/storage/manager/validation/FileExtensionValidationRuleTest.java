package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import com.saltatorv.file.storage.manager.vo.Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.Set;

import static com.saltatorv.file.storage.manager.command.UploadFileCommandObjectMother.uploadTextFileCommand;
import static com.saltatorv.file.storage.manager.validation.ExtensionSetAssembler.buildExtensions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class FileExtensionValidationRuleTest {

    private FileExtensionValidationRule validationRule;

    @BeforeEach
    public void setup() {
        validationRule = null;
    }

    @Test
    @DisplayName("Can throw exception when create rule with empty set of extensions")
    public void canThrowExceptionWhenCreateRuleWithEmptySetOfExtensions() {
        //given
        var extensions = buildExtensions()
                .create();

        //when
        assertThrows(RuntimeException.class, () -> createValidationRule(extensions));

        //then
    }

    @Test
    @DisplayName("Can successfully validate upload file command")
    public void canSuccessfullyValidateUploadFileCommand() {
        //given
        var command = uploadTextFileCommand();

        var extension = createDummyExtension();

        given(extension.isIncludedIn(command.getFileName()))
                .willReturn(true);

        var extensions = buildExtensions()
                .addExtension(extension)
                .create();

        createValidationRule(extensions);

        //when
        validateCommand(command);

        //then
        assertExtensionWasCheckedOnce(extension, command.getFileName());
    }

    @Test
    @DisplayName("Can successfully validate upload file command when rule contain multiple extensions and only one is valid")
    public void canSuccessfullyValidateUploadFileCommandWhenRuleContainMultipleExtensionsAndOnlyOneIsValid() {
        //given
        var command = uploadTextFileCommand();

        var first = createDummyExtension();
        var second = createDummyExtension();

        given(first.isIncludedIn(command.getFileName()))
                .willReturn(false);

        given(second.isIncludedIn(command.getFileName()))
                .willReturn(true);

        var extensions = buildExtensions()
                .addExtension(first)
                .addExtension(second)
                .create();

        createValidationRule(extensions);

        //when
        validateCommand(command);

        //then
        assertExtensionWasCheckedOnce(first, command.getFileName());
        assertExtensionWasCheckedOnce(second, command.getFileName());
    }

    @Test
    @DisplayName("Can throw exception when validation fail")
    public void canThrowExceptionWhenValidationFail() {
        //given
        var command = uploadTextFileCommand();

        var extension = createDummyExtension();

        given(extension.isIncludedIn(command.getFileName()))
                .willReturn(false);

        var extensions = buildExtensions()
                .addExtension(extension)
                .create();

        createValidationRule(extensions);

        //when
        assertThrows(RuntimeException.class, () -> validateCommand(command));

        //then
    }

    private void createValidationRule(Set<Extension> extensions) {
        validationRule = new FileExtensionValidationRule(extensions);
    }

    private void validateCommand(UploadFileCommand command) {
        validationRule.validate(command);
    }

    private Extension createDummyExtension() {
        return mock(Extension.class);
    }

    private void assertExtensionWasCheckedOnce(Extension extension, Path fileName) {
        assertExtensionWasCheckedNTimes(extension, fileName, 1);
    }

    private void assertExtensionWasCheckedNTimes(Extension extension, Path fileName, int expectedTimes) {
        then(extension)
                .should(times(expectedTimes))
                .isIncludedIn(fileName);
    }
}
