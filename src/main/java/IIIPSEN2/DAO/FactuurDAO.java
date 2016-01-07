package IIIPSEN2.DAO;

import IIIPSEN2.model.Factuur;
import IIIPSEN2.model.Order;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Roger Bosman on 9-10-2015.
 *
 * @author Roger Bosman
 *         <p>
 *         <p>
 *         FactuurDAO is een subklassen van IIIPSEN2.DAO.
 *         FactuurDAO verzorgt het ophalen, opslaan en wijzigen van de facturen in de database.
 *         Dit doet FactuurDAO door steeds de connectie op te halen uit de IIIPSEN2.DAO superklasse en hier de statements op
 *         uit te voeren.
 */
public class FactuurDAO extends DAO {
    /**
     * FactuurDAO bevat een aantal vooraf gedefinieerde statements:
     * selectEntireFactuur:        hele factuur selecteren
     * insertFactuur:              factuur toevoegen
     * updateFactuurVerzendStatus: verzendstatus factuur updaten
     */
    private String selectEntireFactuur = "SELECT factuur_order_id, factuur_factuur, factuur_isVerzonden FROM `factuur`";
    private String insertFactuur = "INSERT INTO factuur (factuur_order_id, factuur_factuur, factuur_isVerzonden) " +
            "VALUES (?, ?, ?)";
    private String updateFactuurVerzendStatus = "UPDATE factuur set factuur_isVerzonden = ?";

    /**
     * getFacturenByIsVerzonden haalt facturen op obv de verzendstatis
     *
     * @param isVerzonden boolean verzonden/niet verzonden
     * @return alle facturen behordende bij de gegeven verzendstatus
     * @author Roger Bosman
     */
    public ArrayList<Factuur> getFacturenByIsVerzonden(boolean isVerzonden) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int isVerzondenInt;
        if (isVerzonden) {
            isVerzondenInt = 1;
        } else {
            isVerzondenInt = 0;
        }
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            // Result set get the result of the SQL query
            preparedStatement = connection.prepareStatement(selectEntireFactuur + " WHERE " +
                    "factuur_isVerzonden = ?");
            preparedStatement.setInt(1, isVerzondenInt);
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
     * updateIsVerzondenByOrderID updated de verzendstatus voor een bepaalde factuur behordene bij het gegeven orderid
     *
     * @param isVerzonden de verzendstatus waarnaar de factuur geupdated moet worden
     * @param orderID     het orderid behordene bij de factuur
     * @author Roger Bosman
     */
    public void updateIsVerzondenByOrderID(boolean isVerzonden, int orderID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int isVerzondenInt;
        if (isVerzonden) {
            isVerzondenInt = 1;
        } else {
            isVerzondenInt = 0;
        }
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(updateFactuurVerzendStatus + " WHERE " +
                    "factuur_order_id = ?");
            preparedStatement.setInt(1, isVerzondenInt);
            preparedStatement.setInt(2, orderID);

            // Result set get the result of the SQL query
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
     * insertFactuur voegt facturen toe.
     *
     * @param factuur toe te voegen factuur
     * @author Roger Bosman
     */
    public void insertFactuur(Factuur factuur) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            int isVerzonden;
            if (factuur.isVerzonden()) {
                isVerzonden = 1;
            } else {
                isVerzonden = 0;
            }
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(insertFactuur);
            preparedStatement.setInt(1, factuur.getOrderID());
            preparedStatement.setBinaryStream(2, new FileInputStream(factuur.getFactuur()));
            preparedStatement.setInt(3, isVerzonden);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
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
     * getFactuurByOrder returned de factuur behordene bij een gegeven order
     *
     * @param order op basis van deze order word de factuur opgezocht
     * @return de factuur behorende bij de gegeven order
     * @author Roger Bosman
     */
    public ArrayList<Factuur> getFactuurByOrder(Order order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connection.prepareStatement(selectEntireFactuur + " WHERE " +
                    "factuur_order_id = ?");
            // Result set get the result of the SQL query
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
     * getFactuurByOrder returned actieve facturen op basis van verzendstatus
     *
     * @param isVerzonden op basis van deze verzendstatus worden de facturen opgezocht
     * @return de facturen behorende bij de verzendstatus
     * @author Roger Bosman
     */
    public ArrayList<Factuur> getActieveFaceturenByIsVerzonden(boolean isVerzonden) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int isVerzondenInt;
        if (isVerzonden) {
            isVerzondenInt = 1;
        } else {
            isVerzondenInt = 0;
        }
        try {
            connection = getConnection();
            // Statements allow to issue SQL queries to the database
            // Result set get the result of the SQL query
            preparedStatement = connection.prepareStatement(selectEntireFactuur
                    + ", `order` WHERE factuur_order_id = order_id AND factuur_isVerzonden = ?");
            preparedStatement.setInt(1, isVerzondenInt);
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
     * buildResult build het result op bais van de gegeven resultset
     *
     * @param resultSet de te verwerken resultset
     * @return arraylist van facturen resulterend uit de resultset
     * @author Roger Bosman
     */
    private ArrayList<Factuur> buildResult(ResultSet resultSet) {
        ArrayList<Factuur> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                boolean isVerzonden;
                if (resultSet.getInt(3) == 1) {
                    isVerzonden = true;
                } else {
                    isVerzonden = false;
                }
                Blob blobFactuur = resultSet.getBlob(2);
                InputStream in = blobFactuur.getBinaryStream();
                File fileFactuur = new File("Facturen/tempDAOFactuurOrderID" + resultSet.getInt(1) + ".pdf");
                OutputStream out = new FileOutputStream(fileFactuur);
                byte[] buff = new byte[4096];
                int len = 0;
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }

                Factuur factuur = new Factuur(
                        fileFactuur,
                        isVerzonden,
                        resultSet.getInt(1));
                result.add(factuur);
                out.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
