package mappers;

import model.Order;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Edited by:
 * - Anton
 * - Roger
 */
public class OrderMapper implements ResultSetMapper<Order> {
  @Override
  public Order map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    Order order = new Order();
    order.setOrderID(resultSet.getInt(1));
    order.setKlantEmail(resultSet.getString(2));
    order.setFactuurAdres(resultSet.getString(3));
    order.setOrderDatum(resultSet.getTimestamp(4));
    order.setIsActief(resultSet.getInt(5));
    return order;
  }
}
