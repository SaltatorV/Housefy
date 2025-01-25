package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;
import com.saltatorv.file.storage.manager.exception.FileContainInvalidExtension;
import com.saltatorv.file.storage.manager.exception.FileExtensionValidationRuleSetCanNotBeEmpty;
import com.saltatorv.file.storage.manager.vo.Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.Set;

import static com.saltatorv.file.storage.manager.dto.UploadFileDtoObjectMother.uploadTextFileDto;
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
        assertThrows(FileExtensionValidationRuleSetCanNotBeEmpty.class, () -> createValidationRule(extensions));

        //then
    }

    @Test
    @DisplayName("Can successfully validate upload file dto")
    public void canSuccessfullyValidateUploadFileCommand() {
        //given
        var dto = uploadTextFileDto();

        var extension = createDummyExtension();

        given(extension.isIncludedIn(dto.getFileName()))
                .willReturn(true);

        var extensions = buildExtensions()
                .addExtension(extension)
                .create();

        createValidationRule(extensions);

        //when
        validateCommand(dto);

        //then
        assertExtensionWasCheckedOnce(extension, dto.getFileName());
    }

    @Test
    @DisplayName("Can throw exception when validation fail")
    public void canThrowExceptionWhenValidationFail() {
        //given
        var dto = uploadTextFileDto();

        var extension = createDummyExtension();

        given(extension.isIncludedIn(dto.getFileName()))
                .willReturn(false);

        var extensions = buildExtensions()
                .addExtension(extension)
                .create();

        createValidationRule(extensions);

        //when
        assertThrows(FileContainInvalidExtension.class, () -> validateCommand(dto));

        //then
    }

    private void createValidationRule(Set<Extension> extensions) {
        validationRule = new FileExtensionValidationRule(extensions);
    }

    private void validateCommand(UploadFileDto dto) {
        validationRule.validate(dto);
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
