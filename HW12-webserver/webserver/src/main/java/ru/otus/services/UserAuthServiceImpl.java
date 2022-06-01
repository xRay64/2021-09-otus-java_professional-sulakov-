package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.ClientDao;
import ru.otus.exceptions.WebServerException;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserAuthServiceImpl implements UserAuthService {
    private static final Logger log = LoggerFactory.getLogger(UserAuthServiceImpl.class);
    private final ClientDao clientDao;

    public UserAuthServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new WebServerException(e);
        }
        messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        String passwordHash = DatatypeConverter.printHexBinary(digest);
        log.info("Password hash={}", passwordHash);

        return clientDao.findByLogin(login)
                .map(client -> client.getPasswordMD5().equalsIgnoreCase(passwordHash))
                .orElse(false);
    }

}
