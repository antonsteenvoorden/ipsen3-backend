package service;

import dao.KlantDAO;
import model.Mail;
import model.MailSender;
import org.glassfish.jersey.internal.util.Base64;

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
      String emailZout = email + "KaasIsBaas";
      byte[] encodedBytes = Base64.encode(emailZout.getBytes());
      String mailTekst = "Beste meneer/mevrouw" +
              "Klik hier om uw wachtwoord te resetten http://145.97.16.190:8086/api/klanten/wachtwoord" +
              "Lionsclub Oegstgeest/Warmond" ;
      System.out.println("encodedBytes " + new String(encodedBytes));
      mailSender.setOntvangers(email);
    } catch (AddressException e) {
      e.printStackTrace();
    }
  }

}
