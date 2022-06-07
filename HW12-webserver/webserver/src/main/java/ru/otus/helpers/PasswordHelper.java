package ru.otus.helpers;

import ru.otus.exceptions.WebServerException;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordHelper {
    private PasswordHelper() {
    }

    public static String encodeStringToMD5(String string) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new WebServerException(e);
        }
        messageDigest.update(string.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();

        return DatatypeConverter.printHexBinary(digest);
    }
}
