package ru.otus.service;

import ru.otus.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> getAll();

    Optional<Client> getById(Long id);

    Client saveClient(Client client);
}
