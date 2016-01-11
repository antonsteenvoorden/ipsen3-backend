package mappers;

import model.OrderRegel;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Edited by:
 * - Roger
 */
public class OrderRegelMapper implements ResultSetMapper<OrderRegel> {

    @Override public OrderRegel map(int i, ResultSet resultSet, StatementContext statementContext)
        throws SQLException {
        return null;
    }
}
