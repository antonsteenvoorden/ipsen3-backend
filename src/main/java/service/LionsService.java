package service;

import model.MailSender;
import dao.KlantDAO;
import model.Nieuwsbrief;

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

  public Nieuwsbrief send(Nieuwsbrief nieuwsbrief) {
    //doe sheit uit dennis's barf controller / model
    mailSender.setNieuwsbrief(nieuwsbrief);
    try {
      mailSender.setOntvangers(klantDAO.getEmailAdressen());
    } catch (AddressException e) {
      e.printStackTrace();
    }
    mailSender.sendMail();
    return nieuwsbrief;
  }

}
