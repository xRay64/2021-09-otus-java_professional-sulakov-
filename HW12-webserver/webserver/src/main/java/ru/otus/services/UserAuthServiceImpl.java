package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.ClientDao;
import ru.otus.helpers.PasswordHelper;

public class UserAuthServiceImpl implements UserAuthService {
    private static final Logger log = LoggerFactory.getLogger(UserAuthServiceImpl.class);
    private final ClientDao clientDao;

    public UserAuthServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        String passwordHash = PasswordHelper.encodeStringToMD5(password);

        return clientDao.findByLogin(login)
                .map(client -> client.getPasswordMD5().equalsIgnoreCase(passwordHash))
                .orElse(false);
    }

}
