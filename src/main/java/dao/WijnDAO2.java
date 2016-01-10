package dao;

import mappers.WijnMapper;
import model.Wijn;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Set;

/**
 * Created by roger on 10-1-2016.
 */

@RegisterMapper(WijnMapper.class)
public interface WijnDAO2 {
    @SqlQuery("SELECT w.wijn_id, w.wijn_serie_id, w.wijn_naam, w.wijn_inkoopprijs, w.wijn_prijs, w.wijn_type, " +
            "w.wijn_jaartal, w.wijn_isactief, w.wijn_afkomst_naam, w.wijn_category_naam FROM `wijn` w")
    public Set<Wijn> retrieveAll();

    @SqlQuery("SELECT w.wijn_id, w.wijn_serie_id, w.wijn_naam, w.wijn_inkoopprijs, w.wijn_prijs, w.wijn_type, " +
            "w.wijn_jaartal, w.wijn_isactief, w.wijn_afkomst_naam, w.wijn_category_naam FROM `wijn` w " +
            "WHERE w.wijn_id = :wijnID")
    public Wijn retrieve(@Bind("wijnID") int wijnID);

}
