package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorExceptionist implements Processor {
    private final LocalDateTime localDateTime;

    public ProcessorExceptionist() {
        this.localDateTime = LocalDateTime.now();
    }

    public ProcessorExceptionist(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public Message process(Message message) {
        if (localDateTime.getSecond() % 2 == 0) {
            throw new RuntimeException("Current second is even");
        }
        return message;
    }
}
