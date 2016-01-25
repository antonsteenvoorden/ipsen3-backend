package service;

import dao.KlantDAO;
import model.Klant;

import javax.ws.rs.ForbiddenException;
import java.util.Collection;

/**
 * @author Anton Steenvoorden
 */
public class KlantService extends BaseService<Klant> {
  private final KlantDAO dao;

  public KlantService(KlantDAO dao) {
    this.dao = dao;
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
    return requireResult(dao.get(email));
  }

  public void add(Klant klant) {
    klant.setKlantRechten(true);
    dao.add(klant);
  }

  public void update(Klant authenticator, Klant updateKlant) {
    if (authenticator.getEmail().equals(updateKlant.getEmail())) {
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
      if (updateKlant.isKlantRechten() != existingKlant.isKlantRechten()) {
        existingKlant.setKlantRechten(updateKlant.isKlantRechten());
      }
      if (updateKlant.isLidRechten() != existingKlant.isLidRechten()) {
        existingKlant.setLidRechten(updateKlant.isLidRechten());
      }
      if (updateKlant.isMsRechten() != existingKlant.isMsRechten()) {
        existingKlant.setMsRechten(updateKlant.isMsRechten());
      }
      if (updateKlant.isAdminRechten() != existingKlant.isAdminRechten()) {
        existingKlant.setAdminRechten(updateKlant.isAdminRechten());
      }
      if (updateKlant.isAccountActief() != existingKlant.isAccountActief()) {
        existingKlant.setAccountActief(updateKlant.isAccountActief());
      }
      if (updateKlant.isWantsMail() != existingKlant.isWantsMail()) {
        existingKlant.setWantsMail(updateKlant.isWantsMail());
      }

      dao.update(existingKlant);
    } else {
      throw new ForbiddenException();
    }
  }

//    public void delete(String email) {
//        // Controleren of deze gebruiker wel bestaat
//        Klant klant = get(email, authenticator);
//        //TODO: METHODE AANMAKEN OM KLANTEN INACTIEF TE STELLEN
//        dao.delete(email);
//    }
}
