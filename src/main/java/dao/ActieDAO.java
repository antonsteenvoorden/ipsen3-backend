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
 */
@RegisterMapper(ActieMapper.class)
public interface ActieDAO {

  @SqlUpdate("INSERT INTO `actie` (`actie_start_timestamp`, `actie_eind_timestamp`, `actie_referentie_naam`, " +
          "`actie_beschrijving`) VALUES (:startTimestamp, :eindTimestamp, :referentieNaam, :beschrijving);")
  void add(@BindBean Actie actie);

  @SqlQuery("SELECT actie_id, actie_isactief, actie_start_timestamp, actie_eind_timestamp, actie_referentie_naam, " +
          "actie_beschrijving FROM actie WHERE actie_id = :id")
  Actie get(@Bind("id") int id);

  @SqlQuery("SELECT actie_id, actie_isactief, actie_start_timestamp, actie_eind_timestamp, actie_referentie_naam, " +
          "actie_beschrijving FROM actie")
  Collection<Actie> getAll();

  @SqlUpdate("UPDATE actie SET actie_isactief = :actieActief, actie_start_timestamp = :startTimestamp, " +
          "actie_eind_timestamp = :eindTimestamp, actie_referentie_naam = :referentieNaam," +
          "actie_beschrijving = :beschrijving WHERE actie_id = :id")
  void update(@BindBean Actie actie);

  @SqlQuery("SELECT actie_id, actie_isactief, actie_start_timestamp, actie_eind_timestamp, actie_referentie_naam, " +
          "actie_beschrijving FROM actie WHERE actie_isactief = 1")
  Collection<Actie> getActive();

}
