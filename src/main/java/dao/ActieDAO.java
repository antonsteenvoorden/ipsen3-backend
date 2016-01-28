package dao;

import mappers.ActieMapper;
import model.Actie;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Collection;

/**
 * Created by Anton on 20/01/2016.
 * DAO die met de tabel actie praat.
 * Maakt gebruik van JDBI.
 * Gebruikt de ActieMapper om de opgehaalde resultset om te zetten in de actie objecten.
 */
@RegisterMapper(ActieMapper.class)
public interface ActieDAO {
  /**
   * Voegt een actie toe aan de database, ontvangt een Actie bean
   * @param actie
     */
  @SqlUpdate("INSERT INTO `actie` (`actie_isactief`, `actie_start_timestamp`, `actie_eind_timestamp`, `actie_referentie_naam`, " +
          "`actie_beschrijving`) VALUES (:actieActief, :startTimestamp, :eindTimestamp, :referentieNaam, :beschrijving);")
  void add(@BindBean Actie actie);

  /**
   * Vraagt een enkele actie op aan de hand van het actie nummer (id)
   * @param id
   * @return Actie
     */
  @SqlQuery("SELECT actie_id, actie_isactief, actie_start_timestamp, actie_eind_timestamp, actie_referentie_naam, " +
          "actie_beschrijving FROM actie WHERE actie_id = :id")
  Actie get(@Bind("id") int id);

  /**
   * Vraagt alle acties op uit de database
   * @return Collection<Actie>
     */
  @SqlQuery("SELECT actie_id, actie_isactief, actie_start_timestamp, actie_eind_timestamp, actie_referentie_naam, " +
          "actie_beschrijving FROM actie")
  Collection<Actie> getAll();

  /**
   * Wijzigt een actie door de nieuw opgegeven waarden
   * @param actie
     */
  @SqlUpdate("UPDATE actie SET actie_isactief = :actieActief, actie_start_timestamp = :startTimestamp, " +
          "actie_eind_timestamp = :eindTimestamp, actie_referentie_naam = :referentieNaam," +
          "actie_beschrijving = :beschrijving WHERE actie_id = :id")
  void update(@BindBean Actie actie);

  /**
   * Haalt alle actieve acties op uit de database
   * @return Collection<Actie>
     */
  @SqlQuery("SELECT actie_id, actie_isactief, actie_start_timestamp, actie_eind_timestamp, actie_referentie_naam, " +
          "actie_beschrijving FROM actie WHERE actie_isactief = 1")
  Collection<Actie> getActive();

}
