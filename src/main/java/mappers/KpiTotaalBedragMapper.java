package mappers;

import model.KpiTotaalBedrag;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by roger on 31-1-2016.
 */
public class KpiTotaalBedragMapper implements ResultSetMapper<KpiTotaalBedrag> {
  @Override
  public KpiTotaalBedrag map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    KpiTotaalBedrag kpiTotaalBedrag = new KpiTotaalBedrag();
    kpiTotaalBedrag.setTotaalBedrag(resultSet.getInt("orderRegelTotaal"));
    return kpiTotaalBedrag;
  }
}
