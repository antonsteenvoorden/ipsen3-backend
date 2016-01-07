package IIIPSEN2.DAO;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import IIIPSEN2.model.Klant;
import IIIPSEN2.model.Order;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Roger Bosman, Anton Steenvoorden, Sidney de Geus
 *         <p>
 *         KlantDAO is een subklassen van IIIPSEN2.DAO.
 *         KlantDAO verzorgt het ophalen, opslaan en wijzigen van de klanten in de database.
 *         Dit doet KlantDAO door steeds de connectie op te halen uit de IIIPSEN2.DAO superklasse en hier de statements op uit
 *         te voeren.
 */

public class KlantDAO extends DAO {
    /**
     * KlantDAO bevat een aantal vooraf gedefinieerde statements:
     * selectEntireKlant:  hele klant selecteren
     * importKlant:        klant importeren
     * insertKlant:        klant toevoegen
     * updateKlant:        klant updaten
     * getKlantGyString:   klant ophalen op basis van een string
     */
    private String selectEntireKlant = "SELECT klant_email, klant_voornaam, klant_tussenvoegsel, klant_achternaam, " +
            "klant_straatnaam, klant_huisnummer, klant_huisnummer_toevoeging, klant_postcode, " +
            "klant_postcode_toevoeging, klant_plaatsnaam, klant_telefoon, klant_gastlid, klant_notitie, " +
            "klant_isactief , klant_date FROM klant";

    private String importKlant =
            "INSERT INTO klant ("
                    + "klant_email, "
                    + "klant_voornaam, "
                    + "klant_tussenvoegsel, " +
                    "klant_achternaam, " +
                    "klant_straatnaam, "
                    + "klant_huisnummer, "
                    + "klant_postcode, " +
                    "klant_postcode_toevoeging, "
                    + "klant_plaatsnaam, "
                    + "klant_gastlid, "
                    + "klant_isactief, "
                    + "klant_date)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    private String insertKlant =
            "INSERT INTO klant ("
                    + "klant_email, "
                    + "klant_voornaam, "
                    + "klant_tussenvoegsel, " +
                    "klant_achternaam, " +
                    "klant_straatnaam, "
                    + "klant_huisnummer, "
                    + "klant_huisnummer_toevoeging, "
                    + "klant_postcode, " +
                    "klant_postcode_toevoeging, "
                    + "klant_plaatsnaam, "
                    + "klant_telefoon, "
                    + "klant_gastlid, "
                    + "klant_notitie, "
                    + "klant_isactief) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private String updateKlant = "UPDATE klant SET klant_voornaam = ?, klant_tussenvoegsel = ?, klant_achternaam = ?," +
            "klant_straatnaam = ?, klant_huisnummer = ?, klant_huisnummer_toevoeging = ?, klant_postcode = ?, " +
            "klant_postcode_toevoeging = ?, klant_plaatsnaam = ?, klant_telefoon = ?, klant_gastlid = ?, " +
            "klant_notitie = ?, " +
            "klant_isactief = ? WHERE klant_email = ?";

    private String getKlantByString = "SELECT klant_email, klant_voornaam, klant_tussenvoegsel, klant_achternaam, " +
            "klant_straatnaam, klant_huisnummer, klant_huisnummer_toevoeging, klant_postcode, " +
            "klant_postcode_toevoeging, klant_plaatsnaam, klant_telefoon, klant_gastlid, klant_notitie, " +
            "klant_isactief, klant_date FROM klant ";


    /**
     * getActieveKlanten returned alle actieve klanten
     *
     * @return alle actieve klanten
     * @author Roger Bosman
     */
    public ArrayList<Klant> getActieveKlanten() {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        ArrayList<Klant> result = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            // Result set get the result of the SQL query
            resultSet = null;
            resultSet = statement.executeQuery(selectEntireKlant + " WHERE klant_isactief = 1");
            result = buildResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    /**
     * getInactieveKlanten returned alle inactieve klanten
     *
     * @return alle inactieve klanten
     * @author Roger Bosman
     */
    public ArrayList<Klant> getInactieveKlanten() {
        Statement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        ArrayList<Klant> result = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery(selectEntireKlant + " WHERE klant_isactief = 0");
            result = buildResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    /**
     * getAlleKlanten returned alle klanten
     *
     * @return alle klanten
     * @author Roger Bosman
     */
    public ArrayList<Klant> getAlleKlanten() {
        Statement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        ArrayList<Klant> result = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery(selectEntireKlant);
            result = buildResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    /**
     * getKlantByOrder returned een klant op basis van de bijbehordende order
     *
     * @param order van de op te halen klant
     * @return klant behorende bij order
     * @author Roger Bosman
     */
    public Klant getKlantByOrder(Order order) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();

            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(selectEntireKlant + " "
                    + "WHERE klant_email = ?");
            preparedStatement.setString(1, order.getKlantEmail());
            ArrayList<Klant> result = buildResult(preparedStatement.executeQuery());
            if (result.size() == 1) {
                return result.get(0);
            } else {
                throw new Exception("Error. Function should return one klant but returned " + result.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * getKlantByEmail returned een klant op basis van bijbehorend email adres
     *
     * @param email adres
     * @return klant behorende bij het email adres
     * @author Roger Bosman
     */
    public Klant getKlantByEmail(String email) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(selectEntireKlant + " WHERE klant_email" +
                    " = ?");
            preparedStatement.setString(1, email);
            ArrayList<Klant> result = buildResult(preparedStatement.executeQuery());
            if (result.size() == 1) {
                return result.get(0);
            } else {
                throw new SQLException("Error. Function should return one klant but returned " + result.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * addKlant voegt een klant toe
     *
     * @param klant toe te voegen klant
     * @author Roger Bosman
     */
    public void addKlant(Klant klant) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(insertKlant);
            preparedStatement.setString(1, klant.getEmail());
            preparedStatement.setString(2, klant.getVoornaam());
            preparedStatement.setString(3, klant.getTussenvoegsel());
            preparedStatement.setString(4, klant.getAchternaam());
            preparedStatement.setString(5, klant.getStraatnaam());
            preparedStatement.setInt(6, klant.getHuisNummer());
            preparedStatement.setString(7, klant.getHuisNummerToevoeging());
            preparedStatement.setInt(8, klant.getPostcode());
            preparedStatement.setString(9, klant.getPostcodeToevoeging());
            preparedStatement.setString(10, klant.getPlaatsNaam());
            preparedStatement.setString(11, klant.getTelefoon());
            preparedStatement.setString(12, klant.getGastLid());
            preparedStatement.setString(13, klant.getNotitie());
            preparedStatement.setInt(14, klant.getIsActief());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er is iets misgegaan bij het schrijven naar de database.");
            alert.setContentText("Controlleer alle velden nog eens ! ");
            alert.showAndWait();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * pubilc method welke de private importklanten aanroept
     *
     * @param klant te importen klant
     * @return geimporte klant
     * @author Roger Bosman
     */
    public int[] importNotAddKlant(Klant klant) {
        return importKlant(klant);
    }


//    + "klant_email, "
//            + "klant_voornaam, "
//            + "klant_tussenvoegsel, " +
//            "klant_achternaam, " +
//            "klant_straatnaam, "
//            + "klant_huisnummer, "
//            + "klant_postcode, " +
//            "klant_postcode_toevoeging, "
//            + "klant_plaatsnaam, "
//            + "klant_gastlid, "
//            + "klant_isactief) "x
//            + "klant_date"

    //Deze functie is alleen voor het importen. Vanwege de veiligheid heb ik hem
    //private gemaakt en door een andere (public) method (importNotAddKlant) aan laten roepen.

    /**
     * importKlant importeert de klanten
     *
     * @param klant te importeren klant
     * @return geimporteerde klant
     */
    private int[] importKlant(Klant klant) {
        Connection connection = null;
        connection = getConnection();
        int adds = 0;
        int updates = 0;
        int ignores = 0;
        boolean selectSuccess = false;

        try {
            Klant selectedKlant = getKlantByEmail(klant.getEmail());
            if (selectedKlant != null) {
                selectSuccess = true;
                // if the match exists, but the status is inactive, put it to active
                if (selectedKlant.getIsActief() == 0) {
                    setActiveOnImport(selectedKlant.getEmail());
                    updates++;
                } else {
                    ignores++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            selectSuccess = false;
        }

        if (!selectSuccess) {
            PreparedStatement preparedStatement = null;
            try {
                // Statements allow to issue SQL queries to the database
                preparedStatement = connection.prepareStatement(importKlant);
                preparedStatement.setString(1, klant.getEmail());
                preparedStatement.setString(2, klant.getVoornaam());
                preparedStatement.setString(3, klant.getTussenvoegsel());
                preparedStatement.setString(4, klant.getAchternaam());
                preparedStatement.setString(5, klant.getStraatnaam());
                preparedStatement.setInt(6, klant.getHuisNummer());
                preparedStatement.setInt(7, klant.getPostcode());
                preparedStatement.setString(8, klant.getPostcodeToevoeging());
                preparedStatement.setString(9, klant.getPlaatsNaam());
                preparedStatement.setString(10, klant.getGastLid());
                preparedStatement.setInt(11, klant.getIsActief());
                preparedStatement.setTimestamp(12, klant.getCreationDate());

                preparedStatement.executeUpdate();
                adds++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } else {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        int[] results = {adds, updates, ignores};
        return results;
    }

    /**
     * setActiveOnImport word gebruikt door importKlant om klanten inactief te stellen
     *
     * @param email behordene bij klant
     */
    private void setActiveOnImport(String email) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            String updateKlantStatusByEmail = "UPDATE klant "
                    + "SET klant_isactief = ? "
                    + "WHERE klant_email = ?";
            preparedStatement = connection.prepareStatement(updateKlantStatusByEmail);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * updateKlant updated een klant
     *
     * @param klant te updaten klant
     * @author Roger Bosman
     */
    public void updateKlant(Klant klant) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(updateKlant);
            preparedStatement.setString(1, klant.getVoornaam());
            preparedStatement.setString(2, klant.getTussenvoegsel());
            preparedStatement.setString(3, klant.getAchternaam());
            preparedStatement.setString(4, klant.getStraatnaam());
            preparedStatement.setInt(5, klant.getHuisNummer());
            preparedStatement.setString(6, klant.getHuisNummerToevoeging());
            preparedStatement.setInt(7, klant.getPostcode());
            preparedStatement.setString(8, klant.getPostcodeToevoeging());
            preparedStatement.setString(9, klant.getPlaatsNaam());
            preparedStatement.setString(10, klant.getTelefoon());
            preparedStatement.setString(11, klant.getGastLid());
            preparedStatement.setString(12, klant.getNotitie());
            preparedStatement.setInt(13, klant.getIsActief());
            preparedStatement.setString(14, klant.getEmail());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er is iets misgegaan bij het schrijven naar de database.");
            alert.setContentText("Controlleer alle velden nog eens ! ");
            alert.showAndWait();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * getKlantByString returned een klant op bais van een string
     *
     * @param klantString string op basis waarvan gezocht moet worden
     * @return arraylist van klanten behorende bij de string
     */
    public ArrayList<Klant> getKlantByString(String klantString) {
        ArrayList<Klant> result = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(getKlantByString + "WHERE klant_achternaam LIKE ? OR " +
                    "klant_voornaam LIKE ? OR klant_email LIKE ?");
            preparedStatement.setString(1, "%" + klantString + "%");
            preparedStatement.setString(2, "%" + klantString + "%");
            preparedStatement.setString(3, "%" + klantString + "%");
            resultSet = preparedStatement.executeQuery();
            result = buildResult(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    /**
     * enableLogging zet de logging functie aan.
     *
     * @author Roger Bosman
     */
    public void enableLogging() {
        Statement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            // Result set get the result of the SQL query
            statement.executeQuery("SET global general_log = 1;");
            //statement.executeQuery("SET global log_output = 'general_log';");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * buildResult maakt het resultaat van de resultSet.
     *
     * @param resultSet te builden resultset
     * @return gebuilde resultset
     * @author Roger Bosman
     */
    private ArrayList<Klant> buildResult(ResultSet resultSet) {
        ArrayList<Klant> result = new ArrayList();
        try {
            while (resultSet.next()) {
                Klant klant = new Klant(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getInt(8),
                        resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getString(13),
                        resultSet.getInt(14),
                        resultSet.getTimestamp(15));
                result.add(klant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * getDebiteuren returned de debiteuren
     *
     * @return de debiteuren
     */
    public ArrayList<String> getDebiteuren() {
        Connection connection = null;
        ArrayList<String> debiteuren = new ArrayList<String>();

        String select = "SELECT k.klant_voornaam, k.klant_tussenvoegsel, k.klant_achternaam, "
                + "k.klant_email, o.order_id, o.order_order_datum, "
                + "FORMAT(SUM(orderregel_wijnprijs * 6 * orderregel_aantal), 2) AS totalPrice";
        String from = " FROM klant k ";
        String leftJoin = "LEFT JOIN `order` o ON k.klant_email = o.order_klantemail "
                + "LEFT JOIN orderregel ON o.order_id = orderregel.orderregel_orderid ";
        String where = "WHERE o.order_isactief = 1 ";
        String groupby = "GROUP BY k.klant_email ";
        String orderby = "ORDER BY k.klant_achternaam ";

        String query = select + from + leftJoin + where + groupby + orderby;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                while (resultSet.next()) {
                    String fullname = resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet
                            .getString(3);
                    String orderid = Integer.toString(resultSet.getInt(5));
                    String email = resultSet.getString(4);
                    String totalPrice = resultSet.getString(7);
                    String date = resultSet.getDate(6).toString();
                    debiteuren.add(""
                            + "" + fullname + ";"
                            + "" + orderid + ";"
                            + "" + email + ";"
                            + "" + totalPrice + ";"
                            + "" + date
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return debiteuren;
    }
}