package service;

import dao.KlantDAO;
import model.Klant;

import javax.ws.rs.ForbiddenException;
import java.util.Collection;

/**
 *
 * @author Peter van Vliet
 */
public class KlantService extends BaseService<Klant> {
    private final KlantDAO dao;

    public KlantService(KlantDAO dao) {
        this.dao = dao;
    }

    public Collection<Klant> getAll() {
        return dao.getAll();
    }

    public Klant get(String email) {
        return requireResult(dao.get(email));
    }

    public void add(Klant klant) {
        klant.setKlantRechten(true);
        dao.add(klant);
    }

    public void update(String email, Klant authenticator, Klant klant) {
        if(email.equals(klant.getEmail())) {
            if (!authenticator.hasRole("ADMIN")) {
                // Vaststellen dat de geauthenticeerde gebruiker
                // zichzelf aan het aanpassen is, tenzij het een admin is
                assertSelf(authenticator, klant);
            }
            dao.update(klant);
        } else {
            throw new ForbiddenException();
        }
    }

    public void delete(String email) {
        // Controleren of deze gebruiker wel bestaat
        Klant klant = get(email);
        //TODO: METHODE AANMAKEN OM KLANTEN INACTIEF TE STELLEN
        dao.delete(email);
    }
}
