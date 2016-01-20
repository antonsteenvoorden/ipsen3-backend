package dao;

import mappers.InschrijvingMapper;
import model.Actie;
import model.Inschrijving;
import model.Klant;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Anton on 20/01/2016.
 */
@RegisterMapper(InschrijvingMapper.class)
public interface InschrijvingDAO {

    @SqlUpdate("INSERT INTO account_inschrijving (actie_id, account_email) VALUES (:actie, :email);")
    void add(@Bind int actie, @BindBean Klant klant);
}
