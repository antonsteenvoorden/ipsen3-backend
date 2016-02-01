package dao;

import mappers.KpiTotaalBedragMapper;
import model.KpiTotaalBedrag;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Set;

/**
 Edited by:
 * -Jordan
 * -Sidney
 * -Dennis
 * -Roger
 */
@RegisterMapper(KpiTotaalBedragMapper.class)
public interface KpiTotaalBedragDAO {
  /**
   * Haalt een set van KpiTotaalBedragen op. Deze worden later pas bij elkaar opgeteld.
   * @return
   */
  @SqlQuery("SELECT orderregel_aantal * orderregel_wijnprijs as \"orderRegelTotaal\" FROM `orderregel`")
  Set<KpiTotaalBedrag> get();
}
