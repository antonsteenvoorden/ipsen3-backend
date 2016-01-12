package service;

import dao.KlantDAO;
import model.Klant;
import service.BaseService;

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
        klant.setKlant(true);

        dao.add(klant);
    }

    public void update(Klant authenticator, String email, Klant klant) {
        // Controleren of deze gebruiker wel bestaat
        Klant oldUser = get(email);

        if (!authenticator.hasRole("ADMIN")) {
            // Vaststellen dat de geauthenticeerde gebruiker
            // zichzelf aan het aanpassen is, tenzij het een admin is
            assertSelf(authenticator, oldUser);
        }

        dao.update(email, klant);
    }

    public void delete(String email) {
        // Controleren of deze gebruiker wel bestaat
        Klant klant = get(email);
        //TODO: METHODE AANMAKEN OM KLANTEN INACTIEF TE STELLEN
        dao.delete(email);
    }
}
