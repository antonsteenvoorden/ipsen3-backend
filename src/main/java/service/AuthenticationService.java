package service;

import dao.KlantDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import com.google.common.base.Optional;
import model.Klant;


/**
 * @author Anton Steenvoorden
 */
public class AuthenticationService
    implements Authenticator<BasicCredentials, Klant>, Authorizer<Klant> {
    private final KlantDAO klantDAO;

    public AuthenticationService(KlantDAO klantDAO) {
        this.klantDAO = klantDAO;
    }

    @Override
    public Optional<Klant> authenticate(BasicCredentials credentials) throws AuthenticationException {
        System.out.println("AuthenticationService.authenticate");
        Klant klant = klantDAO.getAuthStub(credentials.getUsername());

        if (klant != null && klant.getPassword().equals(credentials.getPassword())) {
            System.out.println("AuthenticationService.authenticate: returnging filed user");
            System.out.println("Is klant " + klant.isKlant());
            return Optional.of(klant);
        }
        System.out.println("AuthenticationService.authenticate: returning empty user");
        return Optional.absent();
    }

    @Override
    public boolean authorize(Klant klant, String roleName) {
        return klant.hasRole(roleName);
    }
}
