package by.boris;

public class App {
    public static void main(String[] args) {
        Person adam = new Person();
        Person eva = new Person();

        // Adam добавляет в друзья Eva (с именем Eva@minsk.by и публичным ключем который она передала Adam)
        adam.addFriend("Eva@minsk.by", eva.getPublicKey());

        // Adam отправляет сообщение Eva (Eva получает зашифрованное сообщение)
        String encryptedMessage = adam.sendMessage("Eva@minsk.by", "Hello my friend!");
        System.out.println("Encrypted message: " + encryptedMessage);

        // Eva расшифровывает это сообщение с помощью известного только ей privateKey
        String decryptedMessage = eva.receiveMessage(encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);
    }
}
