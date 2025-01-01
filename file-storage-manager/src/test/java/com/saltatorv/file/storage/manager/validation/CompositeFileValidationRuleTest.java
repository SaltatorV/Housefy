package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.saltatorv.file.storage.manager.command.UploadFileCommandObjectMother.uploadTextFileCommand;
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
    @DisplayName("Can validate command by every validation rule")
    public void canValidateCommandByEveryValidationRule() {
        //given
        var command = uploadTextFileCommand();

        var firstRule = createDummyValidationRule();
        var secondRule = createDummyValidationRule();

        createComposite(firstRule, secondRule);

        //when
        validate(command);

        //then
        assertValidationRuleWasCalledOnce(firstRule, command);
        assertValidationRuleWasCalledOnce(secondRule, command);
    }

    @Test
    @DisplayName("Can break validation loop when one validation fail")
    public void canBreakValidationLoopWhenOneValidationFail() {
        //given
        var command = uploadTextFileCommand();

        var firstRule = createDummyValidationRule();
        var secondRule = createDummyValidationRule();

        doThrow(new RuntimeException("Validation failed"))
                .when(firstRule)
                .validate(command);

        createComposite(firstRule, secondRule);

        //when
        assertThrows(RuntimeException.class, () -> validate(command));

        //then
        assertValidationRuleWasCalledOnce(firstRule, command);
        assertValidationRuleWasNotCalled(secondRule, command);
    }

    private FileValidationRule createDummyValidationRule() {
        return mock(FileValidationRule.class);
    }

    private void createComposite(FileValidationRule... rules) {
        compositeRule = new CompositeFileValidationRule(List.of(rules));
    }

    private void validate(UploadFileCommand command) {
        compositeRule.validate(command);
    }

    private void assertValidationRuleWasCalledOnce(FileValidationRule rule, UploadFileCommand command) {
        assertValidationRuleWasCalledNTimes(rule, command, 1);
    }

    private void assertValidationRuleWasNotCalled(FileValidationRule rule, UploadFileCommand command) {
        assertValidationRuleWasCalledNTimes(rule, command, 0);
    }

    private void assertValidationRuleWasCalledNTimes(FileValidationRule rule, UploadFileCommand command, int times) {
        then(rule)
                .should(times(times))
                .validate(command);
    }
}
