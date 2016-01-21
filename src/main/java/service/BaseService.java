package service;

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

    public void assertSelf(Klant klant1, Klant klant2) {
        if (!klant1.getEmail().equals(klant2.getEmail())) {
            throw new ForbiddenException();
        }
    }

}

