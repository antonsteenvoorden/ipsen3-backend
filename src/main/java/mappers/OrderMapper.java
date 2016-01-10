package mappers;

import model.Order;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Anton on 10/01/2016.
 */
public class OrderMapper implements ResultSetMapper<Order> {
  @Override
  public Order map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    Order order = new Order();
    //TODO:SET PROPERTIES
    return order;
  }
  private boolean intToBool(int number) {
    if (number == 1) {
      return true;
    }
    return false;
  }
}
