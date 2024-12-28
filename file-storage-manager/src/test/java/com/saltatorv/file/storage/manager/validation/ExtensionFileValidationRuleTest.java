package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import com.saltatorv.file.storage.manager.vo.Extension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Set;

import static com.saltatorv.file.storage.manager.command.UploadFileCommandAssembler.buildUploadFileCommand;
import static com.saltatorv.file.storage.manager.validation.ExtensionSetAssembler.buildExtensions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class ExtensionFileValidationRuleTest {

    private ExtensionFileValidationRule validationRule;

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
        var command = buildUploadFileCommand("test.txt")
                .withDestination("/test")
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        var extension = createDummyExtension();

        given(extension.isIncludedIn(command.getFileName().getDestination()))
                .willReturn(true);

        var extensions = buildExtensions()
                .addExtension(extension)
                .create();

        createValidationRule(extensions);

        //when
        validateCommand(command);

        //then
        assertExtensionWasCheckedOnce(extension, command.getFileName().getDestination());
    }

    @Test
    @DisplayName("Can successfully validate upload file command when rule contain multiple extensions and only one is valid")
    public void canSuccessfullyValidateUploadFileCommandWhenRuleContainMultipleExtensionsAndOnlyOneIsValid() {
        //given
        var command = buildUploadFileCommand("test.txt")
                .withDestination("/test")
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        var first = createDummyExtension();
        var second = createDummyExtension();

        given(first.isIncludedIn(command.getFileName().getDestination()))
                .willReturn(false);

        given(second.isIncludedIn(command.getFileName().getDestination()))
                .willReturn(true);

        var extensions = buildExtensions()
                .addExtension(first)
                .addExtension(second)
                .create();

        createValidationRule(extensions);


        //when
        validateCommand(command);

        //then
        assertExtensionWasCheckedOnce(first, command.getFileName().getDestination());
        assertExtensionWasCheckedOnce(second, command.getFileName().getDestination());
    }

    @Test
    @DisplayName("Can throw exception when validation fail")
    public void canThrowExceptionWhenValidationFail() {
        //given
        var command = buildUploadFileCommand("img.png")
                .withDestination("/test")
                .withContent("[[255, 255, 255], [128, 128, 128], ...]")
                .withCreateDirectories(true).create();

        var extension = createDummyExtension();

        given(extension.isIncludedIn(command.getFileName().getDestination()))
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
        validationRule = new ExtensionFileValidationRule(extensions);
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

    private void assertExtensionWasNotChecked(Extension extension, Path fileName) {
        assertExtensionWasCheckedNTimes(extension, fileName, 0);
    }

    private void assertExtensionWasCheckedNTimes(Extension extension, Path fileName, int expectedTimes) {
        then(extension)
                .should(times(expectedTimes))
                .isIncludedIn(fileName);
    }
}
