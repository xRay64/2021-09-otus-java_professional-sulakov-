package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
        DateTimeProvider dateTimeProvider = Mockito.mock(DateTimeProvider.class);
        Mockito.when(dateTimeProvider.getDate())
                .thenReturn(testTime);

        ProcessorExceptionist processorExceptionist = new ProcessorExceptionist(dateTimeProvider);

        assertThatThrownBy(() -> processorExceptionist.process(message))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Current second is even");
    }

    @Test
    @DisplayName("should not throw exception if second is not even")
    void shouldNotThrowException() {
        LocalDateTime testTime = LocalDateTime.now();
        testTime = testTime.withSecond(3);
        DateTimeProvider dateTimeProvider = Mockito.mock(DateTimeProvider.class);
        Mockito.when(dateTimeProvider.getDate())
                .thenReturn(testTime);

        ProcessorExceptionist processorExceptionist = new ProcessorExceptionist(dateTimeProvider);

        assertThatCode(() -> processorExceptionist.process(message))
                .doesNotThrowAnyException();
    }
}