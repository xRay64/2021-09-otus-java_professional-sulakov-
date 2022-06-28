package ru.otus.crm.service;

import org.springframework.stereotype.Service;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.repository.AddressRepository;
import ru.otus.crm.repository.ClientRepository;
import ru.otus.crm.repository.PhoneRepository;
import ru.otus.crm.sessionmanager.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final TransactionManager transactionManager;

    public ClientServiceImpl(ClientRepository clientRepository, TransactionManager transactionManager) {
        this.clientRepository = clientRepository;
        this.transactionManager = transactionManager;
    }

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
