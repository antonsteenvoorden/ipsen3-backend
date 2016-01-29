package service;

import dao.WijnDAO;
import model.Wijn;

import java.util.Collection;
import java.util.Set;

/**
 * Edited by:
 * - Anton
 * - Roger
 * Haalt alle wijnen op uit de dao, of alleen de actieve wijnen, of slechts een enkele wijn.
 */
public class WijnService extends BaseService<Wijn> {
    private final WijnDAO dao;

    public WijnService(WijnDAO wijnDao) {
        this.dao = wijnDao;
    }

  /**
   * Maakt een call naar de dao om alle wijnen op te halen
   * @return Collection<Wijn>
   */
  public Collection<Wijn> retrieveAll() {
        return dao.retrieveAll();
    }

    /**
     * Maakt een call naar de dao om alleen de actieve wijnen op te halen
     * @return Collection<Wijn>
     */
    public Collection<Wijn> retreiveActive() {
        return dao.retreiveActive();
    }

    /**
     * Maakt een call naar de dao om alleen de opgevraagde wijn (aan de hand van het
     * wijn serie nummer) op te halen
     * @return Wijn
     */
    public Wijn retrieve(int id) {
        return requireResult(dao.retrieve(id));
    }
}
