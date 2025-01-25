package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.dto.UploadFileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.saltatorv.file.storage.manager.dto.UploadFileDtoObjectMother.uploadTextFileDto;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompositeFileValidationRuleTest {

    private CompositeFileValidationRule compositeRule;

    @BeforeEach
    public void setup() {
        compositeRule = null;
    }

    @Test
    @DisplayName("Can validate upload file dto by every validation rule")
    public void canValidateCommandByEveryValidationRule() {
        //given
        var dto = uploadTextFileDto();

        var firstRule = createDummyValidationRule();
        var secondRule = createDummyValidationRule();

        createComposite(firstRule, secondRule);

        //when
        validate(dto);

        //then
        assertValidationRuleWasCalledOnce(firstRule, dto);
        assertValidationRuleWasCalledOnce(secondRule, dto);
    }

    @Test
    @DisplayName("Can break validation loop when one validation fail")
    public void canBreakValidationLoopWhenOneValidationFail() {
        //given
        var dto = uploadTextFileDto();

        var firstRule = createDummyValidationRule();
        var secondRule = createDummyValidationRule();

        doThrow(new RuntimeException("Validation failed"))
                .when(firstRule)
                .validate(dto);

        createComposite(firstRule, secondRule);

        //when
        assertThrows(RuntimeException.class, () -> validate(dto));

        //then
        assertValidationRuleWasCalledOnce(firstRule, dto);
        assertValidationRuleWasNotCalled(secondRule, dto);
    }

    private FileValidationRule createDummyValidationRule() {
        return mock(FileValidationRule.class);
    }

    private void createComposite(FileValidationRule... rules) {
        compositeRule = new CompositeFileValidationRule(List.of(rules));
    }

    private void validate(UploadFileDto dto) {
        compositeRule.validate(dto);
    }

    private void assertValidationRuleWasCalledOnce(FileValidationRule rule, UploadFileDto dto) {
        assertValidationRuleWasCalledNTimes(rule, dto, 1);
    }

    private void assertValidationRuleWasNotCalled(FileValidationRule rule, UploadFileDto dto) {
        assertValidationRuleWasCalledNTimes(rule, dto, 0);
    }

    private void assertValidationRuleWasCalledNTimes(FileValidationRule rule, UploadFileDto dto, int times) {
        then(rule)
                .should(times(times))
                .validate(dto);
    }
}
