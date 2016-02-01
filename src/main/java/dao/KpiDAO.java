package dao;

import mappers.KpiMapper;
import model.Kpi;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by roger on 31-1-2016.
 */
@RegisterMapper(KpiMapper.class)
public interface KpiDAO {
  @SqlQuery("SELECT count(klant_email) AS aantalKlanten, count(order_id) as aantalOrders FROM `klant`, `order`")
  Kpi get();
}
