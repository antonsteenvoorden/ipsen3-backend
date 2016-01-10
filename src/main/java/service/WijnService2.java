package service;

import dao.WijnDAO;
import dao.WijnDAO2;
import model.Wijn;

import java.util.Collection;
import java.util.Set;

/**
 * Created by roger on 10-1-2016.
 */
public class WijnService2 extends BaseService<Wijn> {
    private final WijnDAO2 dao;

    public WijnService2(WijnDAO2 wijnDAO) {
        this.dao = wijnDAO;
    }

    public Set<Wijn> retrieveAll() {
        return dao.retrieveAll();
    }

    public Wijn retrieve(int id) {
        return requireResult(dao.retrieve(id));
    }
}