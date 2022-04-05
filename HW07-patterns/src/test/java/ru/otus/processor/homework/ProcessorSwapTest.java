package ru.otus.processor.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProcessorSwap")
class ProcessorSwapTest {
    private static final String ORIGINAL_FIELD11_VALUE = "field11";
    private static final String ORIGINAL_FIELD12_VALUE = "field12";

    private Message message;

    @BeforeEach
    void setUp() {
        message = new Message.Builder(1L)
                .field11(ORIGINAL_FIELD11_VALUE)
                .field12(ORIGINAL_FIELD12_VALUE)
                .build();
    }

    @Test
    @DisplayName("should switch 11 and 12 fields")
    void shouldProcess() {
        ProcessorSwap processorSwap = new ProcessorSwap();
        Message processedMessage = processorSwap.process(message);

        assertThat(processedMessage.getField11())
                .isNotNull()
                .isEqualTo(ORIGINAL_FIELD12_VALUE);

        assertThat(processedMessage.getField12())
                .isNotNull()
                .isEqualTo(ORIGINAL_FIELD11_VALUE);
    }
}