package IIIPSEN2.DAO;

import IIIPSEN2.model.Klant;
import IIIPSEN2.model.Order;
import IIIPSEN2.model.OrderRegel;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Roger Bosman, Jordan Munk, Anton Steenvoorden, Sidney de Geus
 *         <p>
 *         OrderDAO is een subklassen van IIIPSEN2.DAO.
 *         OrderDAO verzorgt het ophalen, opslaan en wijzigen van de orders in de database.
 *         Dit doet OrderDAO door steeds de connectie op te halen uit de IIIPSEN2.DAO superklasse en hier de statements op uit
 *         te voeren.
 */

public class OrderDAO extends DAO {
    /**
     * KlantDAO bevat een aantal vooraf gedefinieerde statements:
     * selectEntireOrder:  hele Order selecteren
     * insertOrder:        Order toevoegen
     * updateOrder:        Order updaten
     */
    private String selectEntireOrder = "SELECT order_id, order_klantemail, order_factuuradres, order_order_datum, " +
            "order_isactief FROM `order`";
    private String insertOrder = "INSERT INTO `order` (order_klantemail, order_factuuradres, order_isactief ) VALUES " +
            "(?,?,?)";
    private String updateOrder = "UPDATE `order` SET order_klantemail = ?, order_factuuradres = ?, order_isactief = ?" +
            " " +
            "WHERE order_id = ?";

    /**
     * getActieveOrders returned alle actieve orders     *
     *
     * @return alle actieve orders
     * @author Roger Bosman
     */
    public ArrayList<Order> getActieveOrders() {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery(selectEntireOrder + " WHERE order_isactief = 1");
            return buildResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * getInactieveOrders returned alle inactieve orders     *
     *
     * @return alle inactieve orders
     * @author Roger Bosman
     */
    public ArrayList<Order> getInactieveOrders() {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery(selectEntireOrder + " WHERE order_isactief = 0");
            return buildResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * getAlleOrders returned alle orders     *
     *
     * @return alle orders
     * @author Roger Bosman
     */
    public ArrayList<Order> getAlleOrders() {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery(selectEntireOrder);
            return buildResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * getOrderByKlant returned een order op basis van een klant     *
     *
     * @param klant waarop gezocht wrod
     * @return gevonden order op basis van klant
     * @author Roger Bosman
     */
    public ArrayList<Order> getOrderByKlant(Klant klant) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection
                    .prepareStatement(selectEntireOrder + " WHERE order_klantemail = ?");
            preparedStatement.setString(1, klant.getEmail());
            resultSet = preparedStatement.executeQuery();
            return buildResult(resultSet);
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
        return null;
    }

    /**
     * addOrder voegt een order toe in de database
     *
     * @param order toe te voegen order
     * @return toegevoegde order
     * @author Roger Bosman
     */
    public Order addOrder(Order order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            int orderID;
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(insertOrder, Statement
                    .RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, order.getKlantEmail());
            preparedStatement.setString(2, order.getFactuurAdres());
            preparedStatement.setInt(3, order.getIsActief());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderID = generatedKeys.getInt(1);
                    Order resultOrder = getOrderByID(orderID).get(0);
                    return resultOrder;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
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
     * updateOrder updated een order
     *
     * @param order up de daten order
     * @author Roger Bosman
     */
    public void updateOrder(Order order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(updateOrder);

            preparedStatement.setString(1, order.getKlantEmail());
            preparedStatement.setString(2, order.getFactuurAdres());
            preparedStatement.setInt(3, order.getIsActief());
            preparedStatement.setInt(4, order.getOrderID());
            preparedStatement.executeUpdate();

            OrderRegelDAO ord = new OrderRegelDAO();
            for (OrderRegel o : ord.getOrderRegelByOrder(order)) {
                o.setOrderID(order.getOrderID());
                o.setIsActief(order.getIsActief());
                ord.updateOrderRegel(o);
            }

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
    private ArrayList<Order> buildResult(ResultSet resultSet) {
        ArrayList<Order> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(5));
                order.setOrderDatum(resultSet.getTimestamp(4));
                order.setOrderID(resultSet.getInt(1));
                result.add(order);
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
     * getOrderByID returned een order op basis van een id
     *
     * @param id waar op te zoeken
     * @return gevonden orders
     */
    public ArrayList<Order> getOrderByID(int id) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(selectEntireOrder + "WHERE order_id = ?");
            preparedStatement.setInt(1, id);
            // Result set get the result of the SQL query
            resultSet = preparedStatement.executeQuery();
            return buildResult(resultSet);
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
        return null;
    }
}
