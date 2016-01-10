package service;

import dao.WijnDAO;
import model.Wijn;

import java.util.Collection;

/**
 * Created by roger on 10-1-2016.
 */
public class WijnService extends BaseService<Wijn> {
    private final WijnDAO dao;

    public WijnService(WijnDAO wijnDAO) {
        this.dao = wijnDAO;
    }

    public Collection<Wijn> getAll() {
        return dao.getAll();
    }

    public Wijn get(int id) {
        return requireResult(dao.get(id));
    }
}
