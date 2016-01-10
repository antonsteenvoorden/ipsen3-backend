package dao;

import model.TestObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roger on 10-1-2016.
 */
public class TestDao {
    private String[] stringArray = new String[]{"default0", "default1", "default2", "default3", "default4", "default5", "default6", "default7", "default8", "default9"};

    public void put(int id, TestObject testObject) {
        System.out.println("TestDao.put, id = " + id);
        stringArray[id] = testObject.getTestString();
    }

    public List<TestObject> getAll() {
        List<TestObject> list = new ArrayList<>();
        for (String s : stringArray) {
            TestObject testObject = new TestObject();
            testObject.setTestString(s);
            list.add(testObject);
        }
        return list;
    }

    public TestObject get(int id) {
        TestObject testObject = new TestObject();
        testObject.setTestString(stringArray[id]);
        return testObject;
    }

}
