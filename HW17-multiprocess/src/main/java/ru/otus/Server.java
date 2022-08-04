package ru.otus;

import io.grpc.ServerBuilder;
import ru.otus.grpcService.SequenceService;
import ru.otus.service.SequenceGeneratorImpl;

import java.io.IOException;

public class Server {
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        var sequenceGenerator = new SequenceGeneratorImpl();
        var sequenceService = new SequenceService(sequenceGenerator);

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(sequenceService)
                .build();


        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}
