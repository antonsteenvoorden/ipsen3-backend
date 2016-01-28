package service;

import dao.KlantDAO;
import model.Klant;
import model.Mail;
import model.MailSender;
import org.apache.commons.lang3.RandomStringUtils;
import javax.mail.internet.AddressException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Anton on 13/01/2016.
 */
public class LionsService {
  MailSender mailSender;
  KlantDAO klantDAO;

  public LionsService(String username, String password, KlantDAO klantDAO) {
    mailSender = new MailSender(username, password);
    this.klantDAO = klantDAO;
  }

  public Mail send(Mail mail) {
    //doe sheit uit dennis's barf controller / model
    mailSender.setNieuwsbrief(mail);
    try {
      mailSender.setOntvangers(klantDAO.getEmailAdressen());
    } catch (AddressException e) {
      e.printStackTrace();
    }
    mailSender.sendMail();
    return mail;
  }

  public void wachtwoordVergeten(String email) {
    try {
      Mail mail = new Mail();
      mail.setOnderwerp("Wachtwoord resetten Lions club");
      String newPassword = RandomStringUtils.randomAlphanumeric(8);
      String password = HashService.getHash(newPassword);

      Klant tmpKlant = new Klant();
      tmpKlant.setEmail(email);
      tmpKlant.setPassword(password);
      klantDAO.updateWachtwoord(tmpKlant);

      String mailTekst = "Beste meneer/mevrouw" +
              "Uw nieuwe wachtwoord is: " + newPassword + " Voor gebruikersnaam: " + email +
              "Lionsclub Oegstgeest/Warmond" ;
      mail.setTekst(mailTekst);
      mailSender.setNieuwsbrief(mail);
      mailSender.setOntvangers(email);
      mailSender.sendMail();
    } catch (AddressException e) {
      e.printStackTrace();
    }
  }

}
