package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProcessorExceptionistTest {
    private final Message message = new Message.Builder(1L).build();


    @Test
    @DisplayName("should throw exception if second is even")
    void shouldThrowException() {
        LocalDateTime testTime = LocalDateTime.now();
        testTime = testTime.withSecond(2);

        ProcessorExceptionist processorExceptionist = new ProcessorExceptionist(testTime);

        assertThatThrownBy(() -> processorExceptionist.process(message))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Current second is even");
    }

    @Test
    @DisplayName("should not throw exception if second is not even")
    void shouldNotThrowException() {
        LocalDateTime testTime = LocalDateTime.now();
        testTime = testTime.withSecond(3);

        ProcessorExceptionist processorExceptionist = new ProcessorExceptionist(testTime);

        assertThatCode(() -> processorExceptionist.process(message))
                .doesNotThrowAnyException();
    }
}