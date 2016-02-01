package mappers;

import model.Inschrijving;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Edited by:
 * - Anton
 * <p/>
 * InschrijvingMapper mapt de resultset van de InschrijvingDAO naar een Inschrijving
 *
 * @see dao.InschrijvingDAO
 * @see Inschrijving
 */
public class InschrijvingMapper implements ResultSetMapper<Inschrijving> {

  @Override
  public Inschrijving map(int i, ResultSet resultSet, StatementContext statementContext)
      throws SQLException {
    Inschrijving inschrijving = new Inschrijving();
    inschrijving.setActie_id(resultSet.getInt("actie_id"));
    inschrijving.setKlant_email(resultSet.getString("klant_email"));
    inschrijving.setActieInschrijvingTimestamp(resultSet.getString("actie_inschrijving_timestamp"));

    return inschrijving;
  }
}
