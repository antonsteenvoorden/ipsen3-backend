package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Anton on 28/01/2016.
 */
public class HashService {
    public static String getHash(String password) {
        MessageDigest md = null;
        StringBuffer sb = null;
        try {
            md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());
            byte[] digest = md.digest();
            sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
