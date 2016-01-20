package mappers;

import model.Inschrijving;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Anton on 20/01/2016.
 */
public class InschrijvingMapper implements ResultSetMapper<Inschrijving> {

    @Override public Inschrijving map(int i, ResultSet resultSet, StatementContext statementContext)
        throws SQLException {
        Inschrijving inschrijving = new Inschrijving();


        return inschrijving;
    }
}
