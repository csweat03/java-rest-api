package me.christian.utility;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class CryptographyUtility {

    public static class Base64 {

        public static String encrypt(String data) {
            return java.util.Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
        }

        public static String decrypt(String data) {
            return new String(java.util.Base64.getDecoder().decode(data));
        }
    }

    public static class AES256 {
        private final String KEY, SALT;

        public AES256(String key, String salt) {
            this.KEY = key;
            this.SALT = salt;
        }

        public final String encrypt(String data) {
            return Base64.encrypt(crypt(true, data));
        }

        public final String decrypt(String data) {
            return crypt(false, data);
        }

        private String crypt(boolean encrypt, String data) {
            try {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                KeySpec keySpec = new PBEKeySpec(KEY.toCharArray(), SALT.getBytes(), 65536, 256);

                cipher.init(encrypt?1:2,
                        new SecretKeySpec(secretKeyFactory.generateSecret(keySpec).getEncoded(), "AES"),
                        new IvParameterSpec(new byte[10]));

                return new String(cipher.doFinal(encrypt ?
                        data.getBytes(StandardCharsets.UTF_8) :
                        Base64.decrypt(data).getBytes()));
            } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException |
                     BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException exceptions) {
                throw new RuntimeException(exceptions);
            }
        }

    }

    public static class SecureRandom {
        public String encrypt(int length) {
            try {
                String valid = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                java.security.SecureRandom secureRandom = java.security.SecureRandom.getInstanceStrong();

                return secureRandom
                        .ints(length, 0, valid.length())
                        .mapToObj(valid::charAt)
                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                        .toString();
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                System.err.println("\nCRITICAL: Application has not successfully generated the required API Key.\n");
                noSuchAlgorithmException.printStackTrace();
                System.exit(-1);
                return "";
            }
        }
    }

    public static String generateNewApiKey() {
        return new SecureRandom().encrypt(64);
    }

    public static AES256 encryptionMethod1(Object obj1, Object obj2, Object obj3) {
        String key = CryptographyUtility.Base64.encrypt(String.format("%s-%s-%s", obj1, obj2, obj3)), salt = CryptographyUtility.Base64.encrypt(String.format("%s-%s-%s", obj3, key, obj2));

        return new CryptographyUtility.AES256(key, salt);
    }
}
