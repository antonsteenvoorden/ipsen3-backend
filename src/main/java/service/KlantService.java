//package service;
//
//import dao.KlantDAO;
//import model.Klant;
//
//import java.util.Collection;
//
///**
// *
// * @author Peter van Vliet
// */
//public class KlantService extends BaseService<Klant> {
//    private final KlantDAO dao;
//
//    public KlantService(KlantDAO dao) {
//        this.dao = dao;
//    }
//
//    public Collection<Klant> getAll() {
//        return dao.getAlleKlanten();
//    }
//
//    public Klant get(String email) {
//        return requireResult(dao.getKlantByEmail(email));
//    }
//
//    public void add(Klant klant) {
//        klant.setRoles(new String[] { "GUEST" });
//
//        dao.addKlant(klant);
//    }
//
//    public void update(Klant authenticator, int id, Klant klant) {
//        // Controleren of deze gebruiker wel bestaat
//        Klant oldUser = get(id);
//
//        if (!authenticator.hasRole("ADMIN")) {
//            // Vaststellen dat de geauthenticeerde gebruiker
//            // zichzelf aan het aanpassen is
//            assertSelf(authenticator, oldUser);
//        }
//
//        dao.updateKlant(id, klant);
//    }
//
//    public void delete(int id) {
//        // Controleren of deze gebruiker wel bestaat
//        Klant klant = get(id);
//        //TODO: METHODE AANMAKEN OM KLANTEN INACTIEF TE STELLEN
//        dao.updateKlant(id);
//    }
//}
