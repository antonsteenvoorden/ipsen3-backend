package dao;

import mappers.KlantMapper;
import model.Klant;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Anton on 11/01/2016.
 */
@RegisterMapper(KlantMapper.class)
public abstract class KlantDAO {

  @SqlQuery("SELECT klant.klant_email, klant_voornaam, klant_tussenvoegsel, klant_achternaam, "
          + "klant_straatnaam, klant_huisnummer, klant_huisnummer_toevoeging, "
          + "klant_postcode, klant_postcode_toevoeging, klant_plaatsnaam, klant_telefoon,"
          + " klant_gastlid, klant_notitie, klant_isactief, klant_date, account_wantsmail, account_isklant, account_islid, account_isms, account_isadmin "
          + "FROM klant, account WHERE klant.klant_email = :klant_email AND account.klant_email = klant.klant_email")
  public abstract Klant get(@Bind("klant_email") String username);


  @SqlQuery("SELECT klant.klant_email, klant.klant_voornaam, klant.klant_tussenvoegsel, klant.klant_achternaam, "
          + "klant.klant_straatnaam, klant.klant_huisnummer, klant.klant_huisnummer_toevoeging, "
          + "klant.klant_postcode, klant.klant_postcode_toevoeging, klant.klant_plaatsnaam, klant.klant_telefoon,"
          + "klant.klant_gastlid, klant.klant_notitie, klant.klant_isactief, klant.klant_date, "
          + "account.account_wantsmail, account.account_isklant, account.account_islid, account.account_isms, account.account_isadmin "
          + "FROM klant INNER JOIN account ON klant.klant_email = account.klant_email")
  public abstract Collection<Klant> getAll();


  @SqlQuery("SELECT klant_email, account_password, account_isklant, " +
          "account_islid, account_isms, account_isadmin FROM account " +
          "WHERE account.klant_email = :klant_email")
  public abstract Klant getAuthStub(@Bind("klant_email") String username);

  @Transaction
  public void add(@BindBean Klant klant){
    inserIntoKlant(klant);
    insertIntoAccount(klant);
  }

  @SqlUpdate("INSERT INTO `klant` (klant_email, klant_voornaam, klant_tussenvoegsel, klant_achternaam, klant_straatnaam, " +
          "klant_huisnummer, klant_huisnummer_toevoeging,klant_postcode, klant_postcode_toevoeging, klant_plaatsnaam, "+
          "klant_telefoon) VALUES (:email, :voornaam, :tussenvoegsel, " +
          ":achternaam, :straatnaam, :huisNummer, :huisNummerToevoeging, :postcode, :postcodeToevoeging,:plaatsNaam, :telefoon)")
  public abstract void inserIntoKlant(@BindBean Klant klant);

  @SqlUpdate("INSERT INTO `account` (account.klant_email, account_password)"+
          "VALUES(:email, :password)")
  public abstract void insertIntoAccount(@BindBean Klant klant);


  @Transaction
  public void update(@BindBean Klant klant){
    updateKlant(klant);
    updateAccount(klant);
  }

  @SqlUpdate("UPDATE klant "
          + "SET "
          + "klant_voornaam = :voornaam,"
          + "klant_tussenvoegsel = :tussenvoegsel,"
          + "klant_achternaam = :achternaam,"
          + "klant_straatnaam = :straatnaam, "
          + "klant_huisnummer = :huisNummer, "
          + "klant_huisnummer_toevoeging = :huisNummerToevoeging,"
          + "klant_postcode = :postcode, "
          + "klant_postcode_toevoeging = :postcodeToevoeging,"
          + "klant_plaatsnaam = :plaatsNaam, "
          + "klant_telefoon =:telefoon, "
          + "klant_gastlid = :gastLid, "
          + "klant_notitie = :notitie, "
          + "klant_isactief = :klantActief "
          + "WHERE klant_email = :email;")
  public abstract void updateKlant(@BindBean Klant klant);

  @SqlUpdate("UPDATE account "
          + "SET "
          + "account_password = :password,"
          + "account_isklant = :klantRechten,"
          + "account_islid = :lidRechten,"
          + "account_isms = :msRechten,"
          + "account_isadmin = :adminRechten,"
          + "account_isactief =:wantsMail "
          + "WHERE klant_email = :email;")
  public abstract void updateAccount(@BindBean Klant klant);

  public abstract void delete(@Bind("klant_email") String email);

  @SqlQuery("SELECT klant.klant_email FROM klant INNER JOIN account ON klant.klant_email = account.klant_email " +
          "WHERE account_wantsmail = 1 ")
  public abstract Collection<Klant> getEmailAdressen();
}
