package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final TransactionManager transactionManager;

    @Override
    public List<Client> getAll() {
        List<Client> clientList = new ArrayList<>();
        clientRepository.findAll().forEach(clientList::add);

        return clientList;
    }

    @Override
    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> clientRepository.save(client));
    }
}
