package service;

import dao.KlantDAO;
import model.Klant;
import model.Mail;
import model.MailSender;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.internet.AddressException;

/**
 * Edited by:
 * - Anton
 * <p/>
 * Service die wordt gebruikt door de LionsResource en de KlantResource(voor het wachtwoord vergeten)
 * Gebruikt voor het verzenden van mail
 */
public class LionsService {
  MailSender mailSender;
  KlantDAO klantDAO;

  public LionsService(String username, String password, KlantDAO klantDAO) {
    mailSender = new MailSender(username, password);
    this.klantDAO = klantDAO;
  }

  /**
   * Ontvangt een Mail, wordt gebruikt om nieuwsbrieven mee te versturen
   * Haalt de ontvangers op uit de klantDAO met de getEmailAdressen methode
   * Geeft de verstuurde mail mee terug voor feedback aan de front end
   *
   * @param mail
   * @return Mail
   */
  public Mail send(Mail mail) {
    mailSender.setNieuwsbrief(mail);
    try {
      mailSender.setOntvangers(klantDAO.getEmailAdressen());
    } catch (AddressException e) {
      e.printStackTrace();
    }
    mailSender.sendMail();
    return mail;
  }

  /**
   * Ontvangt een Mail, wordt gebruikt om het contactformulier mee te versturen
   * verstuurt het naar de email van de lions
   * @param mail
   * @return Mail
   */
  public Mail sendContactFormulier(Mail mail) {
    mailSender.setNieuwsbrief(mail);
    try {
      mailSender.setOntvangers(mailSender.getUsername());
    } catch (AddressException e) {
      e.printStackTrace();
    }
    mailSender.sendMail();
    return mail;
  }



  /**
   * Ontvangt een email adres, hier wordt een random gegenereerd wachtwoord voor weggeschreven
   * in de database en deze wordt ongehashed verzonden met de mail, zodat bij de authenticatie de
   * hashing niet verkeerd gaat.
   * Het email wordt gebonden aan een nieuw klant object dat naar de DAO verstuurd word.
   *
   * @param email
   */
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
      String mailTekst = "Beste meneer/mevrouw, <br><br>" +
          "Uw nieuwe wachtwoord is: " + newPassword + " Voor gebruikersnaam: " + email +
          "<br><br>Lionsclub Oegstgeest/Warmond";
      mail.setTekst(mailTekst);
      mailSender.setNieuwsbrief(mail);
      mailSender.setOntvangers(email);
      mailSender.sendMail();
    } catch (AddressException e) {
      e.printStackTrace();
    }
  }

}
