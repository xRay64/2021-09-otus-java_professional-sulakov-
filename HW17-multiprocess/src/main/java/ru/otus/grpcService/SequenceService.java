package ru.otus.grpcService;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Current;
import ru.otus.protobuf.generated.NumberSeqServiceGrpc;
import ru.otus.protobuf.generated.SeqParams;
import ru.otus.service.SequenceGenerator;

import java.util.List;

public class SequenceService extends NumberSeqServiceGrpc.NumberSeqServiceImplBase {
    private final SequenceGenerator sequenceGenerator;

    public SequenceService(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void get(SeqParams request, StreamObserver<Current> responseObserver) {
        List<Integer> integerList = sequenceGenerator.getFor(request.getFrom(), request.getTo());

        integerList.forEach(i -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Returning new value %d\n", i);
            responseObserver.onNext(toCurrent(i));
        });
        responseObserver.onCompleted();
    }

    private Current toCurrent(int currentInteger) {
        return Current.newBuilder().setNmbr(currentInteger).build();
    }
}
