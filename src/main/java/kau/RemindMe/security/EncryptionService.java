package kau.RemindMe.security;

public interface EncryptionService {
    String encrypt(String value);
    String decrypt(String value);
}
