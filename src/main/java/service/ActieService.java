package service;

import dao.ActieDAO;
import dao.InschrijvingDAO;
import model.Actie;
import model.Inschrijving;
import model.Klant;

import java.util.Collection;
import javax.ws.rs.ClientErrorException;
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
    //checken of de gebruiker nog niet is ingeschreven
    if(!checkIngeschreven(id, authenticator)) {
      if (!authenticator.hasRole("ADMIN")) {
        assertSelf(authenticator, klant);
      }

      inschrijvingDAO.add(id, klant);
    } else {
      //already ingescrheven error ? .>...
      System.out.println("ActieService.aanmelden :  al ingeschreven");
    }
  }

  public Collection<Actie> getActive() {
    return dao.getActive();
  }

  public boolean checkIngeschreven(int id, Klant authenticator ) {
    return inschrijvingDAO.checkIngeschreven(id, authenticator.getEmail());
  }
}
