package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Anton on 28/01/2016.#
 * Statische methode zodat er overal vandaan kan worden gehashed
 */
public class HashService {
  /**
   * Hashed het meegekregen wachtwoord met het MD5 algorithme en zet deze om naar een hexadecimale
   * string zodat er geen vreemde unicode tekens in de database worden geschreven.
   * Returned het wachtwoord maar dan gehashed.
   * @param password
   * @return String password
   */
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
