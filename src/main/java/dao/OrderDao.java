package dao;

import mappers.OrderMapper;
import model.Order;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Set;

/**
 * Created by Anton on 10/01/2016.
 */
@RegisterMapper(OrderMapper.class)
public interface OrderDAO {
  @SqlQuery("SELECT order_id, order_klantemail, order_factuuradres, order_order_datum, order_isactief FROM `order`;")
  Set<Order> retrieveAll();

  @SqlQuery("SELECT order_id, order_klantemail, order_factuuradres, order_order_datum, order_isactief " +
          "FROM `order`WHERE order_id = :orderID")
  Order retrieve(int id);


  @SqlUpdate("INSERT INTO `order` (order_klantemail, order_factuuradres, order_isactief ) VALUES (:klantEmail, :factuurAdres, :isActief);")
  void add(Order order);
}
