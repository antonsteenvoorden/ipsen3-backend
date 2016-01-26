package dao;

import mappers.WijnMapper;
import model.Wijn;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Set;

/**
 * Edited by:
 * - Roger
 */

@RegisterMapper(WijnMapper.class)
public interface WijnDAO {
    @SqlQuery("SELECT wijn_id, wijn_serie_id, wijn_naam, wijn_inkoopprijs, wijn_prijs, "
                  + "wijn_type, wijn_jaartal, wijn_isactief, afkomst_naam,"
                  + " category_naam FROM `wijn`, `wijn_afkomst`, `wijn_category`"
                  + "WHERE wijn.wijn_afkomst = wijn_afkomst.afkomst_id AND wijn_afkomst.category_id = wijn_category.category_id")
    Set<Wijn> retrieveAll();

  @SqlQuery("SELECT wijn_id, wijn_serie_id, wijn_naam, wijn_inkoopprijs, wijn_prijs, "
          + "wijn_type, wijn_jaartal, wijn_isactief, afkomst_naam,"
          + " category_naam FROM `wijn`, `wijn_afkomst`, `wijn_category`"
          + "WHERE wijn.wijn_afkomst = wijn_afkomst.afkomst_id AND "
          + "wijn_afkomst.category_id = wijn_category.category_id AND wijn_serie_id = :wijnID")
    Wijn retrieve(@Bind("wijnID") int wijnID);
}
