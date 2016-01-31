package mappers;

import model.Kpi;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by roger on 31-1-2016.
 */
public class KpiMapper implements ResultSetMapper<Kpi> {
  @Override
  public Kpi map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    Kpi kpi = new Kpi();
    kpi.setAantalKlanten(resultSet.getInt("aantalKlanten"));
    kpi.setAantalOrders(resultSet.getInt("aantalOrders"));
    return kpi;
  }
}
