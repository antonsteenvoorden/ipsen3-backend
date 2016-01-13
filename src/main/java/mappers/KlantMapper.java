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
    //wacht een idee
    if(resultSet.getString("klant_email") != null) {
      klant.setEmail(resultSet.getString("klant_email"));
    }
    if(resultSet.getString("klant_voornaam")!= null){
      klant.setVoornaam((resultSet.getString("klant_voornaam")));
    }

    if(resultSet.getString("klant_tussenvoegsel")!= null){
      klant.setTussenvoegsel((resultSet.getString("klant_tussenvoegsel")));
    }

    if(resultSet.getString("klant_achternaam")!= null){
      klant.setAchternaam((resultSet.getString("klant_achternaam")));
    }

    if(resultSet.getString("klant_straatnaam")!= null){
      klant.setStraatnaam((resultSet.getString("klant_straatnaam")));
    }

    if(resultSet.getInt("klant_huisnummer")!= 0){
      klant.setHuisNummer((resultSet.getInt("klant_huisnummer")));
    }

    if(resultSet.getString("klant_huisnummer_toevoeging")!= null){
      klant.setHuisNummerToevoeging((resultSet.getString("klant_huisnummer_toevoeging")));
    }

    if(resultSet.getInt("klant_postcode")!= 0){
      klant.setVoornaam((resultSet.getString("klant_postcode")));
    }

    if(resultSet.getString("klant_postcode_toevoeging")!= null){
      klant.setPostcodeToevoeging((resultSet.getString("klant_postcode_toevoeging")));
    }

    if(resultSet.getString("klant_plaatsnaam")!= null){
      klant.setPlaatsNaam((resultSet.getString("klant_plaatsnaam")));
    }

    if(resultSet.getString("klant_telefoon")!= null){
      klant.setTelefoon((resultSet.getString("klant_telefoon")));
    }

    if(resultSet.getString("klant_gastlid")!= null){
      klant.setGastLid((resultSet.getString("klant_gastlid")));
    }

    if(resultSet.getString("klant_notitie")!= null){
      klant.setNotitie((resultSet.getString("klant_notitie")));
    }

    if(resultSet.getInt("klant_isactief") < 0){
      klant.setIsActief((resultSet.getInt("klant_isactief")));
    }

    if(resultSet.getString("klant_date")!= null){
      klant.setDateString((resultSet.getString("klant_date")));
    }

    if(resultSet.getString("account_password")!= null){
      klant.setPassword((resultSet.getString("account_password")));
    }

    if(String.valueOf(resultSet.getBoolean("acount_email")).isEmpty()) {
      klant.setInMailingList((resultSet.getBoolean("acount_email")));
    }

    //ROLES
    if(String.valueOf(resultSet.getBoolean("acount_isgast")).isEmpty()) {
      klant.setIsKlant((resultSet.getBoolean("acount_isgast")));
    }


    if(String.valueOf(resultSet.getBoolean("account_islid")).isEmpty()) {
      klant.setIsLid((resultSet.getBoolean("account_islid")));
    }


    if(String.valueOf(resultSet.getBoolean("account_isms")).isEmpty()) {
      klant.setIsMS((resultSet.getBoolean("account_isms")));
    }


    if(String.valueOf(resultSet.getBoolean("account_isadmin")).isEmpty()) {
      klant.setIsAdmin((resultSet.getBoolean("account_isadmin")));
    }
    

//    klant.setEmail(resultSet.getString("klant_email"));
//    klant.setVoornaam(resultSet.getString(2));
//    klant.setTussenvoegsel(resultSet.getString(3));
//    klant.setAchternaam(resultSet.getString(4));
//    klant.setStraatnaam(resultSet.getString(5));
//    klant.setHuisNummer(resultSet.getInt(6));
//    klant.setHuisNummerToevoeging(resultSet.getString(7));
//    klant.setPostcode(resultSet.getInt(8));
//    klant.setPostcodeToevoeging(resultSet.getString(9));
//    klant.setPlaatsNaam(resultSet.getString(10));
//    klant.setTelefoon(resultSet.getString(11));
//    klant.setGastLid(resultSet.getString(12));
//    klant.setNotitie(resultSet.getString(13));
//    klant.setIsActief(resultSet.getInt(14));
//    klant.setDateString(resultSet.getString(15));
//    klant.setPassword(resultSet.getString(16));
//    //klant.setRoles(new String[]={resultSet.getInt(17)});
//    //TODO: ?????
//    klant.setInMailingList(resultSet.getBoolean(18));
    return klant;
  }
}
