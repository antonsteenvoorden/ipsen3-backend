package model;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by Anton on 29/01/2016.
 * Klasse waarmee de gebruiker en wachtwoord naar een gmail authenticatie wordt geconverteerd
 */
public class GMailAuthenticator extends Authenticator {
  String user;
  String pw;

  public GMailAuthenticator(String username, String password) {
    super();
    this.user = username;
    this.pw = password;
  }

  public PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(user, pw);
  }
}
