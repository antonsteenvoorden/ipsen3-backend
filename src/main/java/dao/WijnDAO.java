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
    @SqlQuery("SELECT w.wijn_id, w.wijn_serie_id, w.wijn_naam, w.wijn_inkoopprijs, w.wijn_prijs, w.wijn_type, w.wijn_jaartal, w.wijn_isactief, w.wijn_afkomst_naam, w.wijn_category_naam FROM `wijn` w")
    Set<Wijn> retrieveAll();

    @SqlQuery("SELECT w.wijn_id, w.wijn_serie_id, w.wijn_naam, w.wijn_inkoopprijs, w.wijn_prijs, w.wijn_type, w.wijn_jaartal, w.wijn_isactief, w.wijn_afkomst_naam, w.wijn_category_naam FROM `wijn` w WHERE w.wijn_id = :wijnID")
    Wijn retrieve(@Bind("wijnID") int wijnID);



}
