package service;

import com.google.common.base.Optional;
import dao.KlantDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import model.Klant;


/**
 * Edited by:
 * - Anton
 * - Roger
 */
public class AuthenticationService
    implements Authenticator<BasicCredentials, Klant>, Authorizer<Klant> {
    private final KlantDAO klantDAO;

    public AuthenticationService(KlantDAO klantDAO) {
        this.klantDAO = klantDAO;
    }

  /**
   * Hashed de meegekregen credentials naar MD5 en vergelijkt deze met het wachtwoord in de database
   * Ontvangt een base64 credentials (username:password)
   * @param credentials
   * @return Klant or absent
   * @throws AuthenticationException
   */
    @Override
    public Optional<Klant> authenticate(BasicCredentials credentials) throws AuthenticationException {
        System.out.println("AuthenticationService.authenticate " + credentials.getPassword());
        Klant klant = klantDAO.getAuthStub(credentials.getUsername());
        String password = HashService.getHash(credentials.getPassword());
        System.out.println("Klant password: "+ klant.getPassword());
        System.out.println("uthenticate password: " + password);
        if (klant != null && klant.getPassword().equals(password)) {
            System.out.println("AuthenticationService.authenticate: returning " + klant.toString());
            return Optional.of(klant);
        }
        System.out.println("AuthenticationService.authenticate: returning empty user");
        return Optional.absent();
    }


  /**
   * Controlleert of de klant de opgevraagde rol heeft. Ontvangt een klant object en een rol(String)
   * @param klant
   * @param roleName
   * @return boolean
   */
  @Override
    public boolean authorize(Klant klant, String roleName) {
        return klant.hasRole(roleName);
    }
}
