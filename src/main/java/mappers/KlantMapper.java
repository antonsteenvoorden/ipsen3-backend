package mappers;

import model.Klant;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Edited by:
 * - Roger
 */
public class KlantMapper implements ResultSetMapper<Klant> {

  @Override
  public Klant map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    Klant klant = new Klant();
    klant.setEmail(resultSet.getString(1));
    klant.setVoornaam(resultSet.getString(2));
    klant.setTussenvoegsel(resultSet.getString(3));
    klant.setAchternaam(resultSet.getString(4));
    klant.setStraatnaam(resultSet.getString(5));
    klant.setHuisNummer(resultSet.getInt(6));
    klant.setHuisNummerToevoeging(resultSet.getString(7));
    klant.setPostcode(resultSet.getInt(8));
    klant.setPostcodeToevoeging(resultSet.getString(9));
    klant.setPlaatsNaam(resultSet.getString(10));
    klant.setTelefoon(resultSet.getString(11));
    klant.setGastLid(resultSet.getString(12));
    klant.setNotitie(resultSet.getString(13));
    klant.setIsActief(resultSet.getInt(14));
    klant.setDateString(resultSet.getString(15));
    klant.setPassword(resultSet.getString(16));
    klant.setRole(resultSet.getInt(17));
    //TODO: ?????
    klant.setInMailingList(resultSet.getBoolean(18));
    return klant;
  }
}
