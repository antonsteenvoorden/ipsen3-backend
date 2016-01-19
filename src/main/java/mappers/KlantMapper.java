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

    if(hasColumn(resultSet, "klant_email")) {
      klant.setEmail(resultSet.getString("klant_email"));
    }
    if(hasColumn(resultSet, "klant_voornaam")){
      klant.setVoornaam((resultSet.getString("klant_voornaam")));
    }

    if(hasColumn(resultSet, "klant_tussenvoegsel")){
      klant.setTussenvoegsel((resultSet.getString("klant_tussenvoegsel")));
    }

    if(hasColumn(resultSet, "klant_achternaam")){
      klant.setAchternaam((resultSet.getString("klant_achternaam")));
    }

    if(hasColumn(resultSet, "klant_straatnaam")){
      klant.setStraatnaam((resultSet.getString("klant_straatnaam")));
    }

    if(hasColumn(resultSet, "klant_huisnummer")){
      klant.setHuisNummer((resultSet.getInt("klant_huisnummer")));
    }

    if(hasColumn(resultSet, "klant_huisnummer_toevoeging")){
      klant.setHuisNummerToevoeging((resultSet.getString("klant_huisnummer_toevoeging")));
    }

    if(hasColumn(resultSet, "klant_postcode")){
      klant.setVoornaam((resultSet.getString("klant_postcode")));
    }

    if(hasColumn(resultSet, "klant_postcode_toevoeging")){
      klant.setPostcodeToevoeging((resultSet.getString("klant_postcode_toevoeging")));
    }

    if(hasColumn(resultSet, "klant_plaatsnaam")){
      klant.setPlaatsNaam((resultSet.getString("klant_plaatsnaam")));
    }

    if(hasColumn(resultSet, "klant_telefoon")){
      klant.setTelefoon((resultSet.getString("klant_telefoon")));
    }

    if(hasColumn(resultSet, "klant_gastlid")){
      klant.setGastLid((resultSet.getString("klant_gastlid")));
    }

    if(hasColumn(resultSet, "klant_notitie")){
      klant.setNotitie((resultSet.getString("klant_notitie")));
    }

    if(hasColumn(resultSet, "klant_isactief")){
      klant.setIsActief((resultSet.getInt("klant_isactief")));
    }

    if(hasColumn(resultSet, "klant_date")){
      klant.setDateString((resultSet.getString("klant_date")));
    }

    if(hasColumn(resultSet, "account_password")){
      klant.setPassword((resultSet.getString("account_password")));
    }

    if(hasColumn(resultSet, "acount_email")) {
      klant.setWantsMail((resultSet.getBoolean("acount_email")));
    }

    //ROLES
    if(hasColumn(resultSet, "account_isklant")) {
      klant.setIsKlant((resultSet.getBoolean("account_isklant")));
    }


    if(hasColumn(resultSet, "account_islid")) {
      klant.setIsLid((resultSet.getBoolean("account_islid")));
    }


    if(hasColumn(resultSet, "account_isms")) {
      klant.setIsMS((resultSet.getBoolean("account_isms")));
    }


    if(hasColumn(resultSet, "account_isadmin")) {
      klant.setIsAdmin((resultSet.getBoolean("account_isadmin")));
    }

    if(hasColumn(resultSet, "account_wantsmail")) {
      klant.setWantsMail(resultSet.getBoolean("account_wantsMail"));
    }

    return klant;
  }

  private boolean hasColumn(ResultSet resultSet, String columnName) {
    try {
        resultSet.getObject(columnName);
        return true;
    } catch (SQLException e) {
      return false;
    }
  }
}
