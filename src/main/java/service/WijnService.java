package service;

import dao.WijnDao;
import model.Wijn;

import java.util.Set;

/**
 * Edited by:
 * - Roger
 */
public class WijnService extends BaseService<Wijn> {
    private final WijnDao dao;

    public WijnService(WijnDao wijnDao) {
        this.dao = wijnDao;
    }

    public Set<Wijn> retrieveAll() {
        return dao.retrieveAll();
    }

    public Wijn retrieve(int id) {
        return requireResult(dao.retrieve(id));
    }
}
