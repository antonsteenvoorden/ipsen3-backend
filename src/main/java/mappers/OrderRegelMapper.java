package mappers;

import model.OrderRegel;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Edited by:
 * - Roger
 * <p/>
 * OrderRegelMapper mapt de resultset van de OrderRegelDAO naar een OrderRegel
 *
 * @see dao.OrderRegelDAO
 * @see OrderRegel
 */
public class OrderRegelMapper implements ResultSetMapper<OrderRegel> {

  @Override
  public OrderRegel map(int i, ResultSet resultSet, StatementContext statementContext)
      throws SQLException {
    OrderRegel orderRegel = new OrderRegel();
    orderRegel.setOrderRegelID(resultSet.getInt(1));
    orderRegel.setWijnID(resultSet.getInt(2));
    orderRegel.setWijnNaam(resultSet.getString(3));
    orderRegel.setWijnJaartal(resultSet.getInt(4));
    orderRegel.setAantal(resultSet.getInt(5));
    orderRegel.setWijnPrijs(resultSet.getDouble(6));
    orderRegel.setOrderID(resultSet.getInt(1));
    orderRegel.setIsActief(resultSet.getInt(1));

    return orderRegel;
  }

}
