package service;

import model.MailSender;
import dao.KlantDAO;
import model.Nieuwsbrief;

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
    mailSender.setOntvangers(klantDAO.getEmailAdressen());
    mailSender.sendMail();
    return nieuwsbrief;
  }

}
