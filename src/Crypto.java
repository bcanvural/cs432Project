import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    //static String IV = "";
  //  static String plaintext = ""; /*Note null padding*/
    //static String encryptionKey = "";

//    public AES(String IV,String plaintext,String encryptionKey){
//        this.IV = IV;
//        this.plaintext = plaintext;
//        this.encryptionKey = encryptionKey;
//    }
//    public static void main(String [] args) {
//        try {
//
//            System.out.println("==Java==");
//            System.out.println("plain:   " + plaintext);
//
//            byte[] cipher = encrypt(plaintext, encryptionKey);
//
//            System.out.print("cipher:  ");
//            for (int i=0; i<cipher.length; i++)
//                System.out.print(new Integer(cipher[i])+" ");
//            System.out.println("");
//
//            String decrypted = decrypt(cipher, encryptionKey);
//
//            System.out.println("decrypt: " + decrypted);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static byte[] encrypt(String plainText, String encryptionKey, String IV) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("ISO-8859-1"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("ISO-8859-1")));
        return cipher.doFinal(plainText.getBytes("ISO-8859-1"));
    }

    public static String decrypt(byte[] cipherText, String encryptionKey, String IV) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("ISO-8859-1"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("ISO-8859-1")));
        return new String(cipher.doFinal(cipherText),"ISO-8859-1");
    }

    public static byte[] SHA256(String text){
        MessageDigest hasher = null;
        try {
            hasher = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            hasher.update(text.getBytes("ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hasher.digest();
    }
}