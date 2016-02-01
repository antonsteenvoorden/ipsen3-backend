package mappers;

import model.KpiTotaalBedrag;
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
public class KpiTotaalBedragMapper implements ResultSetMapper<KpiTotaalBedrag> {
  /**
   * Mapt de resultset naar een KpitotaalBedrag object
   * @param i
   * @param resultSet
   * @param statementContext
   * @return
   * @throws SQLException
   */
  @Override
  public KpiTotaalBedrag map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    KpiTotaalBedrag kpiTotaalBedrag = new KpiTotaalBedrag();
    kpiTotaalBedrag.setTotaalBedrag(resultSet.getInt("orderRegelTotaal"));
    return kpiTotaalBedrag;
  }
}
