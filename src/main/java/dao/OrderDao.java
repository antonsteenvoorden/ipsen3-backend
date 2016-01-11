package dao;

import mappers.OrderMapper;
import model.Order;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Set;

/**
 * Edited by:
 * - Anton
 * - Roger
 */
@RegisterMapper(OrderMapper.class)
public interface OrderDao {
  @SqlQuery("SELECT order_id, order_klantemail, order_factuuradres, order_order_datum, order_isactief FROM `order`;")
  Set<Order> retrieveAll();

  @SqlQuery("SELECT order_id, order_klantemail, order_factuuradres, order_order_datum, order_isactief " +
      "FROM `order`WHERE order_id = :orderID")
  Order retrieve(@Bind("orderID") int orderID);

  @SqlUpdate("INSERT INTO `order` (order_klantemail, order_factuuradres, order_isactief ) VALUES (:klantEmail, :factuurAdres, :isActief);")
  @GetGeneratedKeys
  int add(@BindBean Order order);

  @SqlUpdate("UPDATE `order` SET order_klantemail = :klantEmail, order_factuuradres = :factuurAdres, order_isactief = :isActief WHERE order_id = :orderID")
  void update(@BindBean Order order);
}
