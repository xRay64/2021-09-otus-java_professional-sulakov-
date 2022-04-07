package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorExceptionist implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public ProcessorExceptionist(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        LocalDateTime date = dateTimeProvider.getDate();
        if (date.getSecond() % 2 == 0) {
            throw new RuntimeException("Current second is even");
        }
        return message;
    }
}
