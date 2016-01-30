package dao;

import mappers.OrderRegelMapper;
import model.OrderRegel;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Set;

/**
 * Edited by:
 * - Roger
 */
@RegisterMapper(OrderRegelMapper.class)
public interface OrderRegelDAO {

  /**
   * Haalt alle orderregels op
   * @return
   */
  @SqlQuery("SELECT orderregel_id, orderregel_wijnid, orderregel_wijnnaam, orderregel_wijnjaartal, " +
                "orderregel_aantal, orderregel_orderid, orderregel_isactief, orderregel_wijnprijs " +
                "FROM `orderregel`;")
  Set<OrderRegel> retrieveAll();

  /**
   * Haalt alle orders voor een gegeven order (op basis van een orderID) op
   * @param orderID
   * @return
   */
  @SqlQuery("SELECT orderregel_id, orderregel_wijnid, orderregel_wijnnaam, orderregel_wijnjaartal, " +
                "orderregel_aantal, orderregel_orderid, orderregel_isactief, orderregel_wijnprijs " +
                "FROM `orderregel` WHERE orderregel_orderid = :orderID;")
  Set<OrderRegel> retrieve(@Bind("orderID") int orderID);

  /**
   * voegt een orderRegel toe en returned de gegenereerde primary key (orderRegelID)
   * @param orderRegel
   * @return
   */
  @SqlUpdate("INSERT INTO orderregel (orderregel_wijnid, orderregel_wijnnaam, orderregel_wijnjaartal, " +
                 "orderregel_aantal, orderregel_orderid, orderregel_isactief, orderregel_wijnprijs) " +
                 "VALUES (:wijnID, :wijnNaam, :wijnJaartal, :aantal, :orderID, :isActief, :wijnPrijs);")
  @GetGeneratedKeys
  int add(@BindBean OrderRegel orderRegel);
}
