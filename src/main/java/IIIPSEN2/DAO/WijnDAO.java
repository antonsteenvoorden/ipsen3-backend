package IIIPSEN2.DAO;

import IIIPSEN2.model.OrderRegel;
import IIIPSEN2.model.Wijn;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Sidney de Geus, Roger Bosman
 *         <p>
 *         In de WijnDAO class staan meerdere methodes die gerelateerd zijn aan het oproepen,
 *         toevoegen en aanpassen van data in de database. Zodra er dus data van wijnen nodig is,
 *         maak je gebruik van de WijnDAO om dit te realiseren.
 */

public class WijnDAO extends DAO {

    Connection connection;

    private final String[] types = {
            "Wit",
            "Rood",
            "Rosé"
    };

    // split them up in order to add additional lines if required (like getGroothandelBestellingWijnen())
    private String selectWijnen =
            "SELECT "
                    + "w.wijn_id, w.wijn_serie_id, w.wijn_naam, w.wijn_inkoopprijs, w.wijn_prijs, w.wijn_type, "
                    + "w.wijn_jaartal, w.wijn_isactief, "
                    + "w_a.afkomst_naam, w_c.category_naam";
    private String from = " FROM `wijn` w ";
    private String leftJoin = ""
            + "LEFT JOIN wijn_afkomst w_a ON w.wijn_afkomst = w_a.afkomst_id "
            + "LEFT JOIN wijn_category w_c ON w_a.category_id = w_c.category_id ";


    // Selects

    public ArrayList<Wijn> getAllWijnen() {

        String query = selectWijnen + from + leftJoin;
        try {
            this.connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildResult(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //
    public ArrayList<Wijn> getActiveWijnen() {
        String where = "WHERE wijn_isactief = ?";
        String query = selectWijnen + from + leftJoin + where;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildResult(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //
    public ArrayList<Wijn> getInactiveWijnen() {
        String where = "WHERE wijn_isactief = ?";
        String query = selectWijnen + from + leftJoin + where;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildResult(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //
    public int getLastWijnSerieID() {
        String select = "SELECT MAX(wijn_serie_id) AS LastSerieID FROM wijn";
        String query = select + " ";
        int lastSerieID = 0;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            lastSerieID = resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastSerieID + 1;
    }

    //
    public ArrayList<Wijn> getGroothandelBestellingWijnen() {
        String newSelect = selectWijnen + ", SUM(orderregel_aantal) AS total";
        String newLeftJoin = leftJoin + "LEFT JOIN orderregel ord ON ord.orderregel_wijnid = w.wijn_id ";
        String where = "WHERE wijn_isactief = ? ";
        String groupby = "GROUP BY w.wijn_id ";
        String orderby = "ORDER BY w_c.category_naam, w.wijn_serie_id ";
        String query = newSelect + from + newLeftJoin + where + groupby + orderby;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildResult(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //
    public ArrayList<Wijn> getWijnBestellijst() {
        String where = "WHERE wijn_isactief = ? ";
        String orderby = "ORDER BY w_c.category_naam, w.wijn_serie_id ";
        String query = selectWijnen + from + leftJoin + where + orderby;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildResult(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //
    public ArrayList<String> getCategories() {
        String query = "SELECT * FROM wijn_category";

        ArrayList<String> result = new ArrayList<String>();
        Statement statement;
        ResultSet resultSet;
        try {
            connection = getConnection();
            if (connection != null) {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                if (resultSet != null) {
                    try {
                        int i = 0;
                        while (resultSet.next()) {
                            result.add(resultSet.getString(2));
                            i++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //returning null here so that the program keeps functioning
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }

    /*
     * getTypes() returns een string array met de types
     */
    public String[] getTypes() {
        return types;
    }


    // Inserts

    /**
     * addWijn(...) voegt een wijn toe aan de database.
     */
    public int[] addWijn(Wijn wijn) throws SQLException {
        this.connection = getConnection();
        int adds = 0;        //
        int updates = 0;
        int ignores = 0;

        int categoryID = addCategory(wijn.getWijnCategory());
        int afkomstID = addAfkomst(categoryID, wijn.getWijnAfkomst());
        boolean selectSuccess = false;
        // select first, if exists, don't insert
        try {
            String selectWijnBySerieID = "SELECT wijn_serie_id, wijn_isactief FROM wijn "
                    + "WHERE wijn_serie_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectWijnBySerieID);
            preparedStatement.setInt(1, wijn.getWijnSerieID());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            selectSuccess = true;
            // if the match exists, but the status is inactive, put it to active
            if (resultSet.getInt(2) == 0) {
                setActiveOnImport(resultSet.getInt(1));
                updates++;
            } else {
                ignores++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            selectSuccess = false;
        }

        if (!selectSuccess) {
            try {
                String insertWijn = "INSERT INTO wijn "
                        + "(wijn_serie_id, wijn_naam, wijn_inkoopprijs, wijn_prijs, wijn_type, wijn_afkomst,"
                        + "wijn_jaartal, wijn_isactief) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(insertWijn);

                preparedStatement.setInt(1, wijn.getWijnSerieID());
                preparedStatement.setString(2, wijn.getWijnNaam());
                preparedStatement.setDouble(3, wijn.getInkoopPrijs());
                preparedStatement.setDouble(4, wijn.getPrijs());
                preparedStatement.setInt(5, wijn.getWijnType());
                preparedStatement.setInt(6, afkomstID);
                preparedStatement.setInt(7, wijn.getWijnJaartal());
                preparedStatement.setInt(8, wijn.getIsActief());

                preparedStatement.executeUpdate();
                adds++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        int[] results = {adds, updates, ignores};
        return results;
    }


    /**
     * addCategory(...) voegt een category toe in de database.
     */
    private int addCategory(String wijnCategory) {
        int categoryID;
        boolean selectSuccess = false;

        try {
            String selectCategoryID = "SELECT category_id FROM wijn_category "
                    + "WHERE category_naam = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectCategoryID);
            preparedStatement.setString(1, wijnCategory);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            categoryID = resultSet.getInt(1);
            selectSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            categoryID = 1;
        }

        if (!selectSuccess) {
            try {
                String insertCategory = "INSERT INTO wijn_category (category_naam) VALUES (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertCategory);
                preparedStatement.setString(1, wijnCategory);
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                categoryID = resultSet.getInt(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return categoryID;
    }

    /**
     * addAfkomst(...) voegt een afkomst toe in de database.
     */
    private int addAfkomst(int categoryID, String wijnAfkomstNaam) {
        int afkomstID;
        boolean selectSuccess = false;

        try {
            String selectAfkomstID = "SELECT afkomst_id FROM wijn_afkomst "
                    + "WHERE category_id = ? AND afkomst_naam = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectAfkomstID);
            preparedStatement.setInt(1, categoryID);
            preparedStatement.setString(2, wijnAfkomstNaam);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            afkomstID = resultSet.getInt(1);
            selectSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            afkomstID = 1;
        }

        if ((!selectSuccess) && (!wijnAfkomstNaam.equals(""))) {
            try {
                String insertAfkomst = "INSERT INTO wijn_afkomst (category_id, afkomst_naam) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertAfkomst);
                preparedStatement.setInt(1, categoryID);
                preparedStatement.setString(2, wijnAfkomstNaam);
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                afkomstID = resultSet.getInt(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return afkomstID;
    }


    // Update

    /**
     * updateWijn(...) zorgt voor het updaten van een wijn in de database.
     */
    public void updateWijn(Wijn wijn) {
        connection = getConnection();
        int categoryID = addCategory(wijn.getWijnCategory());
        int afkomstID = addAfkomst(categoryID, wijn.getWijnAfkomst());

        try {
            String updateWijn = "UPDATE wijn "
                    + "SET wijn_serie_id = ?, wijn_naam = ?, wijn_inkoopprijs = ?, wijn_prijs = ?, wijn_type = ?, "
                    + "wijn_afkomst = ?, wijn_jaartal = ?, wijn_isactief = ? "
                    + "WHERE wijn_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateWijn);

            //set
            preparedStatement.setInt(1, wijn.getWijnSerieID());
            preparedStatement.setString(2, wijn.getWijnNaam());
            preparedStatement.setDouble(3, wijn.getInkoopPrijs());
            preparedStatement.setDouble(4, wijn.getPrijs());
            preparedStatement.setInt(5, wijn.getWijnType());
            preparedStatement.setInt(6, afkomstID);
            preparedStatement.setInt(7, wijn.getWijnJaartal());
            preparedStatement.setInt(8, wijn.getIsActief());

            //where
            preparedStatement.setInt(9, wijn.getWijnID());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Past de status van een wijn aan in de database (isactief of inactief)
     */
    public void setStatus(Wijn wijn) {
        connection = getConnection();
        try {
            String updateWijn = "UPDATE wijn "
                    + "SET wijn_isactief = ? "
                    + "WHERE wijn_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateWijn);
            preparedStatement.setInt(1, wijn.getIsActief());
            preparedStatement.setInt(2, wijn.getWijnID());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    private void setActiveOnImport(int wijnSerieID) {
        try {
            String updateWijnBySerieID = "UPDATE wijn "
                    + "SET wijn_isactief = ? "
                    + "WHERE wijn_serie_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateWijnBySerieID);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, wijnSerieID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Private Methods

    /**
     * buildResult(...) returned een ArrayList met wijn objecten van de data
     * dat uit de database gehaald is.
     */
    private ArrayList<Wijn> buildResult(ResultSet resultSet) {
        ArrayList<Wijn> result = new ArrayList<Wijn>();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Wijn wijn = new Wijn(
                            resultSet.getInt(2),    // serie_id
                            resultSet.getString(3), // naam
                            resultSet.getDouble(4), // inkoop prijs
                            resultSet.getDouble(5), // prijs
                            resultSet.getInt(6),    // type
                            resultSet.getInt(7),    // jaartal
                            resultSet.getInt(8),    // actief
                            resultSet.getString(9),// afkomst
                            resultSet.getString(10) // category
                    );
                    wijn.setWijnID(resultSet.getInt(1));
                    wijn.setStringWijnType(types[resultSet.getInt(6)]);
                    try {
                        if (resultSet.findColumn("total") == 12) {
                            wijn.setTotaalAantalDozen(resultSet.getInt(12));
                        }
                    } catch (Exception e) {
                        System.out.println("kolom 'total' niet aanwezig");
                    }
                    result.add(wijn);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //returning null here so that the program keeps functioning
                return null;
            }
        }
        return result;
    }

    /*
     * @author roger
     * TODO: dit maken!
     */
    public Wijn getWijnByOrderRegel(OrderRegel orderRegel) {
        // TODO Auto-generated method stub
        return null;
    }
}

