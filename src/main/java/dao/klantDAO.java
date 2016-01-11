package dao;

import model.Klant;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

import java.util.Collection;

/**
 * Created by Anton on 11/01/2016.
 */
public interface KlantDAO {

    @SqlQuery("SELECT klant_email, klant_voornaam, klant_tussenvoegsel, klant_achternaam, "
                  + "klant_straatnaam, klant_huisnummer, klant_huisnummer_toevoeging, "
                  + "klant_postcode, klant_postcode_toevoeging, klant_plaatsnaam, klant_telefoon,"
                  + " klant_gastlid, klant_notitie, klant_isactief , klant_date FROM klant "
                  + "WHERE klant_email = :email")
    Klant get(String username);

    Collection<Klant> getAll();

    void add(Klant klant);

    void update(String email, Klant klant);

    void delete(String email);
}
