package service;

import model.Klant;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

/**
 *  * Edited by:
 * - Anton
 * - Roger
 * @param <T>
 */
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

    public void assertSelf(String klantEmail1, String klantEmail2) {
        if (!klantEmail1.equals(klantEmail2)) {
            throw new ForbiddenException();
        }
    }

    public void assertRole(Klant authenticator, String role) {
        if(!authenticator.hasRole(role)) {
            throw new ForbiddenException();
        }
    }

}

