package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorSwap implements Processor {
    @Override
    public Message process(Message message) {
        String originalField11 = message.getField11();
        String originalField12 = message.getField12();
        return message.toBuilder().field11(originalField12).field12(originalField11).build();
    }
}
