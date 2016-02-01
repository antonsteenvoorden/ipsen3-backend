package dao;

import mappers.KpiMapper;
import model.Kpi;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 Edited by:
 * -Jordan
 * -Sidney
 * -Dennis
 * -Roger
 */
@RegisterMapper(KpiMapper.class)
public interface KpiDAO {
  /**
   * Haalt het aantal klanten en het aantal orders op
   * @return
   */
  @SqlQuery("SELECT count(klant_email) AS aantalKlanten, count(order_id) as aantalOrders FROM `klant`, `order`")
  Kpi get();
}
