package mappers;

import model.Actie;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Edited by:
 * - Anton
 * <p/>
 * Acitemapper mapt de resultset van de ActieDAO naar een Actie
 *
 * @see dao.ActieDAO
 * @see Actie
 */
public class ActieMapper implements ResultSetMapper<Actie> {
  @Override
  public Actie map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    Actie actie = new Actie();
    actie.setId(resultSet.getInt("actie_id"));
    actie.setActieActief(resultSet.getBoolean("actie_isactief"));
    actie.setStartTimestamp(resultSet.getString("actie_start_timestamp"));
    actie.setEindTimestamp(resultSet.getString("actie_eind_timestamp"));
    actie.setReferentieNaam(resultSet.getString("actie_referentie_naam"));
    actie.setBeschrijving(resultSet.getString("actie_beschrijving"));
    return actie;
  }
}
