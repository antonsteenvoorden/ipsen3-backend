package dao;

import model.Klant;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

import java.util.Collection;

/**
 * Created by Anton on 11/01/2016.
 */
public interface KlantDAO {

  @SqlQuery("SELECT klant_email, klant_voornaam, klant_tussenvoegsel, klant_achternaam, "
      + "klant_straatnaam, klant_huisnummer, klant_huisnummer_toevoeging, "
      + "klant_postcode, klant_postcode_toevoeging, klant_plaatsnaam, klant_telefoon,"
      + " klant_gastlid, klant_notitie, klant_isactief , klant_date , account_wantsmail FROM `klant`, `account` "
      + "WHERE klant_email = :email AND account_email = klant_email")
  Klant get(@Bind("klant_email") String username);

  @SqlQuery("SELECT account_email, account_password, account_isklant, " +
          "account_islid, account_isms, account_isadmin FROM account WHERE account_email = :klant_email AND;")
  Klant getAuthStub(@Bind("klant_email") String username);

  Collection<Klant> getAll();

  void add(Klant klant);

  void update(@Bind("klant_email") String email, @BindBean Klant klant);

  void delete(@Bind("klant_email") String email);
}
