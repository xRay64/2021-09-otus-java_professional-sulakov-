package ru.otus.service;

import reactor.core.publisher.Mono;
import ru.otus.model.Client;
import ru.otus.model.ClientDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Mono<List<Client>> getAll();

    Mono<Optional<Client>> getById(Long id);

    void saveClient(ClientDTO client);
}
