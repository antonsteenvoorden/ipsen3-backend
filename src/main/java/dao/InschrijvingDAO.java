package dao;

import mappers.InschrijvingMapper;
import model.Inschrijving;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Collection;

/**
 * Edited by:
 * -Anton
 * <p/>
 * DAO die met de tabel actie_inschrijving praat.
 * Maakt gebruik van JDBI.
 * Gebruikt de InschrijvingMapper om de opgehaalde resultset om te zetten in de Inschrijving objecten.
 */
@RegisterMapper(InschrijvingMapper.class)
public interface InschrijvingDAO {

  /**
   * Kijkt of er een resultaat is voor de opgegeven persoon bij de opgegeven actie
   * Ontvangt een actie nummer, en een email adres
   *
   * @param id
   * @param email
   * @return boolean
   */
  @SqlQuery("SELECT EXISTS(SELECT klant_email FROM actie_inschrijving WHERE klant_email = :email AND actie_id = :id)")
  boolean checkIngeschreven(@Bind("id") int id, @Bind("email") String email);

  /**
   * Voegt een nieuwe inschrijving toe aan de tabel
   * Ontvangt een actie nummer en een email
   *
   * @param id
   * @param email
   */
  @SqlUpdate("INSERT INTO actie_inschrijving (actie_id, klant_email) VALUES (:id, :email);")
  void add(@Bind("id") int id, @Bind("email") String email);

  /**
   * Haalt alle inschrijvingen op voor de opgevraagde actie
   * ontvangt een actie nummer
   *
   * @param id
   * @return Collection<Inschrijving>
   */
  @SqlQuery("SELECT actie_id, klant_email, actie_inschrijving_timestamp FROM actie_inschrijving " +
      "WHERE actie_id = :id")
  Collection<Inschrijving> getAll(@Bind("id") int id);
}
