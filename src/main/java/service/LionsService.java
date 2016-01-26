package service;

import dao.KlantDAO;
import model.Mail;
import model.MailSender;

import javax.mail.internet.AddressException;

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
      //mail.setTekst();
      mailSender.setOntvangers(email);
    } catch (AddressException e) {
      e.printStackTrace();
    }
  }

}
