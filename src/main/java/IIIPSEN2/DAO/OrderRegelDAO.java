package IIIPSEN2.DAO;

import IIIPSEN2.model.Order;
import IIIPSEN2.model.OrderRegel;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Roger Bosman, Jordan Munk, Anton Steenvoorden, Sidney de Geus
 *         <p>
 *         OrderRegelDAO is een subklassen van IIIPSEN2.DAO.
 *         OrderRegelDAO verzorgt het ophalen, opslaan en wijzigen van de orderregels in de database.
 *         Dit doet OrderRegelDAO door steeds de connectie op te halen uit de IIIPSEN2.DAO superklasse en hier de statements
 *         op uit te voeren.
 */

public class OrderRegelDAO extends DAO {
    /**
     * KlantDAO bevat een aantal vooraf gedefinieerde statements:
     * selectEntireOrderRegel:  hele orderregel selecteren
     * insertOrderRegel:        orderregel toevoegen
     * updateOrder:             orderregel updaten
     * removeOrder:             orderregel verwijderen
     */
    private String selectEntireOrderRegel = "SELECT "
            + "orderregel_id, "
            + "orderregel_wijnid, "
            + "orderregel_wijnnaam, "
            + "orderregel_wijnjaartal, "
            + "orderregel_aantal, "
            + "orderregel_wijnprijs, "
            + "orderregel_orderid, "
            + "orderregel_isactief "
            + "FROM `orderregel`";

    private String insertOrderRegel = "INSERT INTO orderregel "
            + "(orderregel_wijnid, "
            + "orderregel_wijnnaam, "
            + "orderregel_wijnjaartal, "
            + "orderregel_aantal, "
            + "orderregel_wijnprijs, "
            + "orderregel_orderid, "
            + "orderregel_isactief) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private String updateOrder = "UPDATE "
            + "orderregel SET "
            + "orderregel_wijnid = ?, "
            + "orderregel_wijnnaam = ?, "
            + "orderregel_wijnjaartal = ?, "
            + "orderregel_aantal = ?, "
            + "orderregel_wijnprijs = ?,"
            + "orderregel_orderid = ?, "
            + "orderregel_isactief = ? "
            + "WHERE orderregel_id = ?";

    private String removeOrder = "DELETE FROM `orderregel`"
            + "where orderregel_id = ?";

    /**
     * getActieveOrderRegels returned alle actieve orderRegels
     *
     * @return alle actieve orderRegels
     * @author Roger Bosman
     */
    public ArrayList<OrderRegel> getActieveOrderRegels() {
        ResultSet resultSet = null;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery(selectEntireOrderRegel + " WHERE orderregel_isactief = 1");
            return buildResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * getInactieveOrderRegels returned alle inactieve orderRegels
     *
     * @return alle inactieve orderRegels
     * @author Roger Bosman
     */
    public ArrayList<OrderRegel> getInactieveOrderRegels() {
        ResultSet resultSet = null;
        Connection connection = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery(selectEntireOrderRegel + " WHERE orderregel_isactief = 0");
            return buildResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * getAlleOrderRegels returned alle orderRegels
     *
     * @return alle orderRegels
     * @author Roger Bosman
     */
    public ArrayList<OrderRegel> getAlleOrderRegels(int orderID) {
        ResultSet resultSet = null;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery(selectEntireOrderRegel + " WHERE orderregel_orderid = " + orderID);
            return buildResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * getOrderRegelByOrder returned een arraylist van orderregels op basis van een order
     *
     * @param order op basis waarvan gezocht word
     * @return gevonden orderregels
     * @author Roger Bosman
     */
    public ArrayList<OrderRegel> getOrderRegelByOrder(Order order) {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection
                    .prepareStatement(selectEntireOrderRegel + " WHERE orderregel_orderid = ?");
            preparedStatement.setInt(1, order.getOrderID());
            resultSet = preparedStatement.executeQuery();
            return buildResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * orderRegel toevoegen
     *
     * @param orderRegel toe te voegen orderregel
     * @return toegevoegde orderregel
     * @author Roger Bosman
     */
    public OrderRegel addOrderRegel(OrderRegel orderRegel) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(insertOrderRegel);
            preparedStatement.setInt(1, orderRegel.getWijnID());
            preparedStatement.setString(2, orderRegel.getWijnNaam());
            preparedStatement.setInt(3, orderRegel.getWijnJaartal());
            preparedStatement.setInt(4, orderRegel.getAantal());
            preparedStatement.setDouble(5, orderRegel.getWijnPrijs());
            preparedStatement.setInt(6, orderRegel.getOrderID());
            preparedStatement.setInt(7, orderRegel.getIsActief());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderRegel.setOrderRegelID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            return orderRegel;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * updateOrderRegel updated een gegeven orderregel in de database
     *
     * @param orderRegel up te daten orderregel
     * @author Roger Bosman
     */
    public void updateOrderRegel(OrderRegel orderRegel) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();

            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(updateOrder);
            preparedStatement.setInt(1, orderRegel.getWijnID());
            preparedStatement.setString(2, orderRegel.getWijnNaam());
            preparedStatement.setInt(3, orderRegel.getWijnJaartal());
            preparedStatement.setInt(4, orderRegel.getAantal());
            preparedStatement.setDouble(5, orderRegel.getWijnPrijs());
            preparedStatement.setInt(6, orderRegel.getOrderID());
            preparedStatement.setInt(7, orderRegel.getIsActief());
            preparedStatement.setInt(8, orderRegel.getOrderRegelID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * removeOrderRegel removed een gegeven orderRegel (op basis van id) uit de database
     *
     * @param orderRegelID om te zoeken op orderregels
     * @author Roger Bosman
     */
    public void removeOrderRegel(int orderRegelID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();

            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(removeOrder);
            preparedStatement.setInt(1, orderRegelID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
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
    private ArrayList<OrderRegel> buildResult(ResultSet resultSet) {
        ArrayList<OrderRegel> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                OrderRegel orderRegel = new OrderRegel(
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getDouble(6),
                        resultSet.getInt(8));
                orderRegel.setOrderRegelID(resultSet.getInt(1));
                orderRegel.setOrderID(resultSet.getInt(7));
                result.add(orderRegel);
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
}


