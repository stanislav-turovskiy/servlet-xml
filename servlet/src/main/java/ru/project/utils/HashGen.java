package ru.project.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGen {

    private final static Logger logger = LoggerFactory.getLogger(HashGen.class);

    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder stringBuffer = new StringBuilder();
            for (int i = 0; i < bytes.length; i++)
                stringBuffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
            throw new RuntimeException(e);
        }
    }
}
