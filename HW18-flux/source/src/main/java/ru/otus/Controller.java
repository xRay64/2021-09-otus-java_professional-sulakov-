package ru.otus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.ClientDTO;
import ru.otus.model.Phone;
import ru.otus.service.ClientService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {
    private final ClientService clientService;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    @GetMapping(value = "client/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Client>> clients() {
        var future =
                CompletableFuture.supplyAsync(clientService::getAll, executor);

        return Mono.fromFuture(future);
    }

    @GetMapping(value = "client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Optional<Client>> getClient(@PathVariable("id") long clientId) {
        var feature =
                CompletableFuture.supplyAsync(() -> clientService.getById(clientId), executor);

        return Mono.fromFuture(feature);
    }

    @PostMapping("/client/add")
    public void addClient(
            @RequestBody ClientDTO clientDTO
    ) {
        log.info("clientDTO in POST to /client/add: {}", clientDTO);
        Client newClient = new Client(
                clientDTO.getName(),
                new Address(clientDTO.getAddress()),
                Arrays.stream(clientDTO.getPhones().split(",")).map(Phone::new).collect(Collectors.toSet())
        );
        clientService.saveClient(newClient);
    }
}
