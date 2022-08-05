package ru.otus;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Current;
import ru.otus.protobuf.generated.NumberSeqServiceGrpc;
import ru.otus.protobuf.generated.SeqParams;

import java.util.concurrent.CountDownLatch;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final int SEQ_START = 0;
    private static final int SEQ_END = 30;
    private static int currentServer = 0;
    private static int current = 0;

    private static final Object locker = new Object();

    public static void main(String[] args) throws InterruptedException {
        var latch = new CountDownLatch(1);
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = NumberSeqServiceGrpc.newStub(channel);
        var seqParams = SeqParams.newBuilder()
                .setFrom(SEQ_START)
                .setTo(SEQ_END)
                .build();
        stub.get(seqParams,
                new StreamObserver<>() {
                    @Override
                    public void onNext(Current value) {
                        System.out.printf("Get from remote %d\n", value.getNmbr());
                        synchronized (locker){
                            currentServer = value.getNmbr();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.err.println(t);
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("\n\nЯ все!");
                        latch.countDown();
                    }
                });

        int lastServerValue = 0;
        for (int i = 0; i <= 50; i++) {

            synchronized (locker){
                int newValue = lastServerValue == currentServer ? 0 : currentServer;
                current = current + newValue + 1;
                lastServerValue = currentServer;
            }

            System.out.printf("currentValue:%d\n", current);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        latch.await();

        channel.shutdown();
    }
}
