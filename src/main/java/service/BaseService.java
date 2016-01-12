package service;

import model.Account;
import model.Klant;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

public class BaseService<T> {
    public T requireResult(T model) {
        if (model == null) {
            throw new NotFoundException();
        }
        return model;
    }

    public void assertSelf(Account klant1, Account klant2) {
        if (!klant1.equals(klant2)) {
            throw new ForbiddenException();
        }
    }
}

