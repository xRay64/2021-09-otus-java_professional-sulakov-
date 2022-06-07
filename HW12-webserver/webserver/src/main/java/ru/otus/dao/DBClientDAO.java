package ru.otus.dao;

import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.util.List;
import java.util.Optional;

public class DBClientDAO implements ClientDao {
    private final DBServiceClient dbServiceClient;

    public DBClientDAO(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public List<Client> getAll() {
        return dbServiceClient.findAll();
    }

    @Override
    public Optional<Client> findById(long id) {
        return dbServiceClient.getClient(id);
    }

    @Override
    public Optional<Client> findByLogin(String login) {
        List<Client> clientsByName = dbServiceClient.findByName(login);
        if (clientsByName.size() >= 1) {
            return Optional.ofNullable(clientsByName.get(0));
        }
        return Optional.empty();
    }

    @Override
    public Client saveClient(Client client) {
        return dbServiceClient.saveClient(client);
    }
}
