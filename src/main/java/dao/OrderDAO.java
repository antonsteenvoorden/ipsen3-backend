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
public interface OrderDAO {
  /**
   * Haalt alle orders op uit de database
   * @return
   */
  @SqlQuery("SELECT order_id, order_klantemail, order_factuuradres, order_order_datum, order_isactief FROM `order`;")
  Set<Order> retrieveAll();

  /**
   * Haalt een specifieke order op basis van orderID op
   * @param orderID
   * @return
   */
  @SqlQuery("SELECT order_id, order_klantemail, order_factuuradres, order_order_datum, order_isactief " +
                "FROM `order`WHERE order_id = :orderID")
  Order retrieve(@Bind("orderID") int orderID);

  /**
   * Voegt een order toe en returned de gegenereerde primary key (orderID)
   * @param order
   * @return
   */
  @SqlUpdate("INSERT INTO `order` (order_klantemail, order_factuuradres, order_isactief ) VALUES (:klantEmail, :factuurAdres, :isActief);")
  @GetGeneratedKeys
  int add(@BindBean Order order);

  /**
   * Updated een order
   * @param order
   */
  @SqlUpdate("UPDATE `order` SET order_klantemail = :klantEmail, order_factuuradres = :factuurAdres, order_isactief = :isActief WHERE order_id = :orderID")
  void update(@BindBean Order order);

  /**
   * Haalt alle orderid's op voor een specifieke klant
   * @param klantEmail
   * @return
   */
  @SqlQuery("SELECT order_id FROM `order` WHERE order_klantemail = :klantEmail")
  Set<Integer> retrieveOrderIDs(@Bind("klantEmail") String klantEmail);
}
