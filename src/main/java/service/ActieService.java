package service;

import dao.ActieDAO;
import dao.InschrijvingDAO;
import dao.KlantDAO;
import model.Actie;
import model.Klant;

import java.util.Collection;

/**
 * Created by Anton on 20/01/2016.
 */
public class ActieService extends BaseService<Actie> {
  private final ActieDAO dao;
  private final InschrijvingDAO inschrijvingDAO;
  private final KlantDAO klantDAO;

  public ActieService(ActieDAO dao, InschrijvingDAO inschrijvingDAO, KlantDAO klantDAO) {
    this.dao = dao;
    this.inschrijvingDAO = inschrijvingDAO;
    this.klantDAO = klantDAO;
  }

  public Collection<Actie> getAll() {
    return dao.getAll();
  }
  public void add(Actie actie) {
    dao.add(actie);
  }

  public void update(Actie actie) {
    dao.update(actie);
  }

  public void aanmelden(int actie, Klant authenticator) {
    Klant oldUser = klantDAO.get(authenticator.getEmail());

    if (!authenticator.hasRole("ADMIN")) {
      // Vaststellen dat de geauthenticeerde gebruiker
      // zichzelf aan het aanpassen is, tenzij het een admin is
      assertSelf(authenticator, oldUser);
    }

    inschrijvingDAO.add(actie, authenticator);
  }
}
