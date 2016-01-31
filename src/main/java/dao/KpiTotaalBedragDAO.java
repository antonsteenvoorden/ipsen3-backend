package dao;

import mappers.KpiTotaalBedragMapper;
import model.KpiTotaalBedrag;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Set;

/**
 * Created by roger on 31-1-2016.
 */
@RegisterMapper(KpiTotaalBedragMapper.class)
public interface KpiTotaalBedragDAO {
  @SqlQuery("SELECT orderregel_aantal * orderregel_wijnprijs as \"orderRegelTotaal\" FROM `orderregel`")
  Set<KpiTotaalBedrag> get();
}
