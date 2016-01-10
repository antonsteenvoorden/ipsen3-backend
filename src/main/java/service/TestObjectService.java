package service;

import dao.TestDao;
import dao.WijnDAO;
import model.TestObject;
import model.Wijn;

import java.util.Collection;

/**
 * Created by roger on 10-1-2016.
 */
public class TestObjectService extends BaseService<TestObject> {
    private final TestDao dao;

    public TestObjectService(TestDao testDao) {
        this.dao = testDao;
    }

    public Collection<TestObject> getAll() {
        return dao.getAll();
    }

    public TestObject get(int id) {
        return requireResult(dao.get(id));
    }

    public void add(int id, TestObject testObject) {
        dao.put(id, testObject);
    }
}
