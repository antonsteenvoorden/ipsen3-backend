package service;

import dao.ActieDAO;
import dao.KlantDAO;
import model.Actie;
import model.Klant;

import java.util.Collection;

/**
 * Created by Anton on 20/01/2016.
 */
public class ActieService extends BaseService<Actie> {
  private final ActieDAO dao;

  public ActieService(ActieDAO dao) {
    this.dao = dao;
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
}
