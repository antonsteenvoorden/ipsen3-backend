/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

/**
 * @author Anton Steenvoorden
 */
public class AuthenticationService
    implements Authenticator<BasicCredentials, Klant>, Authorizer<Klant> {
    private final KlantDAO klantDAO;

    public AuthenticationService(KlantDAO klantDAO) {
        this.klantDAO = klantDAO;
    }

    @Override public Optional<Klant> authenticate(BasicCredentials credentials) throws AuthenticationException {
        Klant klant = klantDAO.get(credentials.getUsername());

        if (klant != null && klant.getPassword().equals(credentials.getPassword())) {
            return Optional.of(klant);
        }

        return Optional.absent();
    }

    @Override public boolean authorize(Klant klant, String roleName) {
        return klant.hasRole(roleName);
    }
}
