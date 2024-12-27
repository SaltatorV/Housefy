package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.command.UploadFileCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.saltatorv.file.storage.manager.command.UploadFileCommandAssembler.buildUploadFileCommand;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class CompositeFileValidationRuleTest {

    private CompositeFileValidationRule compositeRule;

    @Test
    @DisplayName("Can validate command by every validation rule")
    public void canValidateCommandByEveryValidationRule() {
        //given
        var command = buildUploadFileCommand("test.txt")
                .withDestination("/tmp")
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        var firstRule = mock(FileValidationRule.class);
        var secondRule = mock(FileValidationRule.class);

        createComposite(firstRule, secondRule);

        //when
        validate(command);

        //then
        then(firstRule)
                .should(times(1))
                .validate(command);

        then(secondRule)
                .should(times(1))
                .validate(command);
    }

    @Test
    @DisplayName("Can break validation loop when one validation fail")
    public void canBreakValidationLoopWhenOneValidationFail() {
        //given
        var command = buildUploadFileCommand("test.txt")
                .withDestination("/tmp")
                .withContent("Test content")
                .withCreateDirectories(true)
                .create();

        var firstRule = mock(FileValidationRule.class);
        var secondRule = mock(FileValidationRule.class);

        doThrow(new RuntimeException("Validation failed"))
                .when(firstRule)
                .validate(command);

        createComposite(firstRule, secondRule);

        //when
        assertThrows(RuntimeException.class, () -> validate(command));

        //then
        then(firstRule)
                .should(times(1))
                .validate(command);

        then(secondRule)
                .should(times(0))
                .validate(command);
    }

    private void createComposite(FileValidationRule... rules) {
        compositeRule = new CompositeFileValidationRule(List.of(rules));
    }

    private void validate(UploadFileCommand command) {
        compositeRule.validate(command);
    }
}
