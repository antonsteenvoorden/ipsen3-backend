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
 * Gebruikt KlantMapper om de resultset te binden aan de Klant model klasse.
 * Enkele klant ophalen, alle klanten ophalen, alleen de rijen nodig voor de authenticate ophalen,
 * een nieuwe klant invoeren, een klant updaten, alleen het wachtwoord van de klant updaten
 * en het ophalen van de emailadressen van de klanten die mail willen ontvangen.
 */
@RegisterMapper(KlantMapper.class)
public abstract class KlantDAO {

  /**
   * Geeft een enkele klant terug op basis van emailadres
   * Maakt gebruik van een join om uit de tabel klant en account gegevens te halen
   * @param email
   * @return Klant
   */
  @SqlQuery("SELECT klant.klant_email, klant.klant_voornaam, klant.klant_tussenvoegsel, klant.klant_achternaam, "
          + "klant.klant_straatnaam, klant.klant_huisnummer, klant.klant_huisnummer_toevoeging, "
          + "klant.klant_postcode, klant.klant_postcode_toevoeging, klant.klant_plaatsnaam, klant.klant_telefoon,"
          + " klant.klant_gastlid, klant.klant_notitie, klant.klant_isactief, klant.klant_date, account_wantsmail, account_isklant, account_islid, account_isms, account_isadmin, account.account_isactief  "
          + "FROM klant LEFT JOIN account ON account.klant_email = klant.klant_email WHERE klant.klant_email = :email; ")
  public abstract Klant get(@Bind("email") String email);

  /**
   * Geeft alle klanten terug.
   * Maakt gebruik van een join om uit de tabel klant en account gegevens te halen
   * @return Collection<Klant>
   */
  @SqlQuery("SELECT klant.klant_email, klant.klant_voornaam, klant.klant_tussenvoegsel, klant.klant_achternaam, "
          + "klant.klant_straatnaam, klant.klant_huisnummer, klant.klant_huisnummer_toevoeging, "
          + "klant.klant_postcode, klant.klant_postcode_toevoeging, klant.klant_plaatsnaam, klant.klant_telefoon,"
          + "klant.klant_gastlid, klant.klant_notitie, klant.klant_isactief, klant.klant_date, "
          + "account.account_wantsmail, account.account_isklant, account.account_islid, account.account_isms, account.account_isadmin, account.account_isactief "
          + "FROM klant INNER JOIN account ON klant.klant_email = account.klant_email")
  public abstract Collection<Klant> getAll();

  /**
   * Haalt de rijen nodig voor het authentiseren van de klant op
   * ontvangt een emailadres (username)
   * @param username
   * @return Klant
   */
  @SqlQuery("SELECT klant_email, account_password, account_isklant, " +
          "account_islid, account_isms, account_isadmin FROM account " +
          "WHERE account.klant_email = :klant_email AND account_isactief = 1")
  public abstract Klant getAuthStub(@Bind("klant_email") String username);

  /**
   * Transactie om een nieuwe klant toe te voegen,
   * insert eerst de klantgegevens in de klant tabel, en vervolgens aan de hand van het email adres
   * ook in de account tabel.
   * Ontvangt een Klant Bean en haalt hier alle waarden uit op.
   * @param klant
   */
  @Transaction
  public void add(@BindBean Klant klant){
    inserIntoKlant(klant);
    insertIntoAccount(klant);
  }

  /**
   * Insert gegevens in de klant tabel, hoort bij de transactie van het toevoegen van een klant
   * insert in klant tabel
   * Ontvangt een Klant object voor de JDBI connectie
   * @param klant
   */
  @SqlUpdate("INSERT INTO `klant` (klant_email, klant_voornaam, klant_tussenvoegsel, klant_achternaam, klant_straatnaam, " +
          "klant_huisnummer, klant_huisnummer_toevoeging,klant_postcode, klant_postcode_toevoeging, klant_plaatsnaam, "+
          "klant_telefoon) VALUES (:email, :voornaam, :tussenvoegsel, " +
          ":achternaam, :straatnaam, :huisNummer, :huisNummerToevoeging, :postcode, :postcodeToevoeging,:plaatsNaam, :telefoon)")
  public abstract void inserIntoKlant(@BindBean Klant klant);

  /**
   * Insert gegevens in de klant tabel, hoort bij de transactie van het toevoegen van een klant
   * insert in account tabel
   * Ontvangt een Klant object voor de JDBI connectie
   * @param klant
   */
  @SqlUpdate("INSERT INTO `account` (account.klant_email, account_password)"+
          "VALUES(:email, :password)")
  public abstract void insertIntoAccount(@BindBean Klant klant);


  /**
   * Transactie om de klant up te daten in klant tabel en in account tabel
   * Ontvangt een hele klant als object parameter
   * @param klant
   */
  @Transaction
  public void update(@BindBean Klant klant){
    updateKlant(klant);
    updateAccount(klant);
  }

  /**
   * Insert de gegevens van het klantobject in de klant tabel
   * Ontvangt een klant object voor de JDBI
   * @param klant
   */
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

  /**
   * Insert de gegevens van het klantobject in de account tabel
   * Ontvangt een klant object voor de JDBI
   * @param klant
   */
  @SqlUpdate("UPDATE account "
          + "SET "
          + "account_isklant = :klantRechten,"
          + "account_islid = :lidRechten,"
          + "account_isms = :msRechten,"
          + "account_isadmin = :adminRechten,"
          + "account_wantsmail = :wantsMail,"
          + "account_isactief = :accountActief "
          + "WHERE klant_email = :email;")
  public abstract void updateAccount(@BindBean Klant klant);

  /**
   * Update het wachtwoord voor de meegestuurde klant in de account tabel.
   * @param klant
   */
  @SqlUpdate("UPDATE account "
          + "SET account_password = :password " +
          "WHERE klant_email = :email;")
  public abstract void updateWachtwoord(@BindBean Klant klant);

  public abstract void delete(@Bind("klant_email") String email);

  /**
   * Haalt alleen de emailadressen op uit de klant/account tabel waarbij wantsmail 1 is (true)
   * Returned Klant objecten die alleen een emailadres hebben. Dit kon niet direct naar
   * InternetAddress worden gemaakt.
   * @return Collection<Klant>
   */
  @SqlQuery("SELECT klant.klant_email FROM klant INNER JOIN account ON klant.klant_email = account.klant_email " +
          "WHERE account_wantsmail = 1 ")
  public abstract Collection<Klant> getEmailAdressen();
}
