package mappers;

import model.Kpi;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 Edited by:
 * -Jordan
 * -Sidney
 * -Dennis
 * -Roger
 */
public class KpiMapper implements ResultSetMapper<Kpi> {
  /**
   * Mapt de resultset naar een KPI object
   * @param i
   * @param resultSet
   * @param statementContext
   * @return
   * @throws SQLException
   */
  @Override
  public Kpi map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    Kpi kpi = new Kpi();
    kpi.setAantalKlanten(resultSet.getInt("aantalKlanten"));
    kpi.setAantalOrders(resultSet.getInt("aantalOrders"));
    return kpi;
  }
}
