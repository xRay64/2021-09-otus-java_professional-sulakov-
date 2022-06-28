package ru.otus.crm.service;

import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> getAll();

    Optional<Client> getById(Long id);

    Client saveClient(Client client);
}
