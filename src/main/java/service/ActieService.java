package service;

import dao.ActieDAO;
import dao.InschrijvingDAO;
import dao.KlantDAO;
import model.Actie;
import model.Inschrijving;
import model.Klant;

import java.util.Collection;

/**
 * Created by Anton on 20/01/2016.
 */
public class ActieService extends BaseService<Actie> {
  private final ActieDAO dao;
  private final InschrijvingDAO inschrijvingDAO;

  public ActieService(ActieDAO dao, InschrijvingDAO inschrijvingDAO) {
    this.dao = dao;
    this.inschrijvingDAO = inschrijvingDAO;
  }

  public Actie get(int id) {
    return dao.get(id);
  }

  public Collection<Actie> getAll() {
    return dao.getAll();
  }

  public Collection<Inschrijving> getInschrijvingen(int id) {
    return inschrijvingDAO.getAll(id);
  }
  public void add(Actie actie) {
    dao.add(actie);
  }

  public void update(Actie actie) {
    dao.update(actie);
  }

  public void aanmelden(int id, Klant authenticator, Klant klant) {

    if (!authenticator.hasRole("ADMIN")) {
      // Vaststellen dat de geauthenticeerde gebruiker
      // zichzelf aan het aanpassen is, tenzij het een admin is
      assertSelf(authenticator, klant);
    }

    inschrijvingDAO.add(id, klant);
  }

  public Collection<Actie> getActive() {
    return dao.getActive();
  }
}
