package dao;

import model.Wijn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roger on 10-1-2016.
 */
public class WijnDAO extends DAO {

    private String selectWijnen = "SELECT "
                    + "w.wijn_id, w.wijn_serie_id, w.wijn_naam, w.wijn_inkoopprijs, w.wijn_prijs, w.wijn_type, "
                    + "w.wijn_jaartal, w.wijn_isactief, "
                    + "w_a.afkomst_naam, w_c.category_naam ";
    private String fromWijn = "FROM `wijn` w ";

    public List<Wijn> getAll() {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String query = selectWijnen + fromWijn;
        try {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildResult(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    preparedStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Wijn get(int id) {
        return makeSampleWijn();
//        PreparedStatement preparedStatement = null;
//        Connection connection = null;
//        String query = selectWijnen + fromWijn + whereID;
//        try {
//            connection = super.getConnection();
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            return buildSingle(resultSet);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                    preparedStatement.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
    }

    private Wijn makeSampleWijn() {
        Wijn wijn = new Wijn();
        wijn.setWijnID(1);
        wijn.setWijnSerieID(1);
        wijn.setWijnNaam("wijnnaam");
        wijn.setInkoopPrijs(12.00);
        wijn.setPrijs(15.00);
        wijn.setWijnType(1);
        wijn.setWijnJaartal(1994);
        wijn.setActief(intToBool(1));
        wijn.setWijnAfkomst("Je moeder");
        wijn.setWijnCategory("Poep");
        return wijn;
    }

    private List<Wijn> buildResult(ResultSet resultSet) {
        List<Wijn> result = new ArrayList<>();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Wijn wijn = new Wijn();
                    wijn.setWijnID(resultSet.getInt(1));
                    wijn.setWijnSerieID(resultSet.getInt(2));
                    wijn.setWijnNaam(resultSet.getString(3));
                    wijn.setInkoopPrijs(resultSet.getDouble(4));
                    wijn.setPrijs(resultSet.getDouble(5));
                    wijn.setWijnType(resultSet.getInt(6));
                    wijn.setWijnJaartal(resultSet.getInt(7));
                    wijn.setActief(intToBool(resultSet.getInt(8)));
                    wijn.setWijnAfkomst(resultSet.getString(9));
                    wijn.setWijnCategory(resultSet.getString(10));
                    result.add(wijn);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return result;
    }

    private Wijn buildSingle(ResultSet resultSet) {
        Wijn wijn = null;
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    wijn = new Wijn();
                    wijn.setWijnID(resultSet.getInt(1));
                    wijn.setWijnSerieID(resultSet.getInt(2));
                    wijn.setWijnNaam(resultSet.getString(3));
                    wijn.setInkoopPrijs(resultSet.getDouble(4));
                    wijn.setPrijs(resultSet.getDouble(5));
                    wijn.setWijnType(resultSet.getInt(6));
                    wijn.setWijnJaartal(resultSet.getInt(7));
                    wijn.setActief(intToBool(resultSet.getInt(8)));
                    wijn.setWijnAfkomst(resultSet.getString(9));
                    wijn.setWijnCategory(resultSet.getString(10));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return wijn;
    }

    private boolean intToBool(int number) {
        if (number == 1) {
            return true;
        }
        return false;
    }
}
