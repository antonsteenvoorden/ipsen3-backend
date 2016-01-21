package dao;

import mappers.InschrijvingMapper;
import model.Actie;
import model.Inschrijving;
import model.Klant;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Collection;

/**
 * Created by Anton on 20/01/2016.
 */
@RegisterMapper(InschrijvingMapper.class)
public interface InschrijvingDAO {

    @SqlUpdate("INSERT INTO actie_inschrijving (actie_id, klant_email) VALUES (:id, :email);")
    void add(@Bind("id") int id, @BindBean Klant klant);

    @SqlQuery("SELECT actie_id, klant_email, actie_inschrijving_timestamp FROM actie_inschrijving" +
            "WHERE actie_id = :id")
    Collection<Inschrijving> getAll(@Bind("id") int id);
}
