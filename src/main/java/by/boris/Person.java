package by.boris;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Person {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    private Map<String,PublicKey> contacts = new HashMap<>();

    public Person() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void addFriend(String name, PublicKey publicKey) {
        contacts.put(name,publicKey);
    }

    public String sendMessage(String recipient, String message) {
        PublicKey publicKey = contacts.get(recipient);
        if (publicKey == null) {
            throw new RuntimeException("Unknown recipient");
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] msgBytes = message.getBytes(StandardCharsets.UTF_8);
            byte[] encrypted = cipher.doFinal(msgBytes);

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String receiveMessage(String cipherText) {
        byte[] encrypted = Base64.getDecoder().decode(cipherText);

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
