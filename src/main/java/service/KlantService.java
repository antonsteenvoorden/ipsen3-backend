package service;

import dao.KlantDAO;
import model.Klant;
import model.Order;

import javax.ws.rs.ForbiddenException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Edited by:
 * - Anton
 * - Roger
 */
public class KlantService extends BaseService<Klant> {
    private final KlantDAO dao;
    private final OrderService orderService;

    public KlantService(KlantDAO dao, OrderService orderService) {
        this.dao = dao;
        this.orderService = orderService;
    }

    public Collection<Klant> getAll() {
        return dao.getAll();
    }

    public Klant get(String email, Klant authenticator) {
        Klant klant = dao.get(email);
        if (!authenticator.hasRole("ADMIN")) {
            // Vaststellen dat de geauthenticeerde gebruiker
            // zichzelf aan het aanpassen is, tenzij het een admin is
            assertSelf(authenticator, klant);
        }
        return requireResult(klant);
    }

    public void add(Klant klant) {
        klant.setKlantRechten(true);
        klant.setPassword(HashService.getHash(klant.getPassword()));
        dao.add(klant);
    }

    public void update(Klant authenticator, Klant updateKlant) {
            if (!authenticator.hasRole("ADMIN")) {
                // Vaststellen dat de geauthenticeerde gebruiker
                // zichzelf aan het aanpassen is, tenzij het een admin is
                assertSelf(authenticator, updateKlant);
            }

            Klant existingKlant = dao.get(updateKlant.getEmail());
            if (updateKlant.getVoornaam() != null) {
                existingKlant.setVoornaam(updateKlant.getVoornaam());
            }
            if (updateKlant.getTussenvoegsel() != null) {
                existingKlant.setTussenvoegsel(updateKlant.getTussenvoegsel());
            }
            if (updateKlant.getAchternaam() != null) {
                existingKlant.setAchternaam(updateKlant.getAchternaam());
            }
            if (updateKlant.getStraatnaam() != null) {
                existingKlant.setStraatnaam(updateKlant.getStraatnaam());
            }
            if (updateKlant.getHuisNummer() != 0) {
                existingKlant.setHuisNummer(updateKlant.getHuisNummer());
            }
            if (updateKlant.getHuisNummerToevoeging() != null) {
                existingKlant.setHuisNummerToevoeging(updateKlant.getHuisNummerToevoeging());
            }
            if (updateKlant.getPostcode() != 0) {
                existingKlant.setPostcode(updateKlant.getPostcode());
            }
            if (updateKlant.getHuisNummerToevoeging() != null) {
                existingKlant.setHuisNummerToevoeging(updateKlant.getHuisNummerToevoeging());
            }
            if (updateKlant.getPlaatsNaam() != null) {
                existingKlant.setPlaatsNaam(updateKlant.getPlaatsNaam());
            }
            if (updateKlant.getTelefoon() != null) {
                existingKlant.setTelefoon(updateKlant.getTelefoon());
            }
            if (updateKlant.getGastLid() != null) {
                existingKlant.setGastLid(updateKlant.getGastLid());
            }
            if (updateKlant.getNotitie() != null) {
                existingKlant.setNotitie(updateKlant.getNotitie());
            }
            if (updateKlant.getKlantActief() != existingKlant.getKlantActief()) {
                existingKlant.setKlantActief(updateKlant.getKlantActief());
            }
            if (updateKlant.getDateString() != null) {
                existingKlant.setDateString(updateKlant.getDateString());
            }
            if (updateKlant.getPassword() != null) {
                existingKlant.setPassword(updateKlant.getPassword());
            }
            if (updateKlant.getKlantRechten() != null && (updateKlant.getKlantRechten() != existingKlant.getKlantRechten())) {
                existingKlant.setKlantRechten(updateKlant.getKlantRechten());
            }
            if (updateKlant.getLidRechten() != null && (updateKlant.getLidRechten() != existingKlant.getLidRechten())) {
                existingKlant.setLidRechten(updateKlant.getLidRechten());
            }
            if (updateKlant.getMsRechten() != null && (updateKlant.getMsRechten() != existingKlant.getMsRechten())) {
                existingKlant.setMsRechten(updateKlant.getMsRechten());
            }
            if (updateKlant.getAdminRechten() != null && (updateKlant.getAdminRechten() != existingKlant.getAdminRechten())) {
                existingKlant.setAdminRechten(updateKlant.getAdminRechten());
            }
            if (updateKlant.getAccountActief() != null && (updateKlant.getAccountActief() != existingKlant.getAccountActief())) {
                existingKlant.setAccountActief(updateKlant.getAccountActief());
            }
            if (updateKlant.getWantsMail() != null && (updateKlant.getWantsMail() != existingKlant.getWantsMail())) {
                existingKlant.setWantsMail(updateKlant.getWantsMail());
            }
            dao.update(existingKlant);
    }

    public void updateWachtwoord(Klant authenticator, Klant updateKlant) {
        updateKlant.setPassword(HashService.getHash(updateKlant.getPassword()));
        if (authenticator.getEmail().equals(updateKlant.getEmail())) {
            if (!authenticator.hasRole("ADMIN")) {
                // Vaststellen dat de geauthenticeerde gebruiker
                // zichzelf aan het aanpassen is, tenzij het een admin is
                assertSelf(authenticator, updateKlant);
            }
            dao.updateWachtwoord(updateKlant);
        } else {
            throw new ForbiddenException();
        }
    }

    public ArrayList<Order> getOrdersByKlant(String email, boolean orderFill, boolean wijnFill, Klant authenticator) {
        return orderService.getOrdersByKlantEmail(email, orderFill, wijnFill, authenticator);
    }


//    public void delete(String email) {
//        // Controleren of deze gebruiker wel bestaat
//        Klant klant = get(email, authenticator);
//        //TODO: METHODE AANMAKEN OM KLANTEN INACTIEF TE STELLEN
//        dao.delete(email);
//    }
}
