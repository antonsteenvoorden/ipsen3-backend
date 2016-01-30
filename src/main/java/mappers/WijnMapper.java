package mappers;

import model.Wijn;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Edited by:
 * - Roger
 * WijnMapper mapt de resultset van de WijnDAO naar een Wijn
 * @see dao.WijnDAO
 * @see Wijn
 */
public class WijnMapper implements ResultSetMapper<Wijn> {
    @Override
    public Wijn map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        Wijn wijn = new Wijn();
        wijn.setWijnID(resultSet.getInt(1));
        wijn.setWijnSerieID(resultSet.getInt(2));
        wijn.setWijnNaam(resultSet.getString(3));
        wijn.setInkoopPrijs(resultSet.getDouble(4));
        wijn.setPrijs(resultSet.getDouble(5));
        wijn.setWijnType(resultSet.getInt(6));
        wijn.setWijnJaartal(resultSet.getInt(7));
        wijn.setActief(resultSet.getInt(8));
        wijn.setWijnAfkomst(resultSet.getString(9));
        wijn.setWijnCategory(resultSet.getString(10));
        return wijn;
    }
}
