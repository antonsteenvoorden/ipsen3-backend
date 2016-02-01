package dao;

import mappers.WijnMapper;
import model.Wijn;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Collection;

/**
 * Edited by:
 * - Anton
 * - Roger
 * <p/>
 * DAO die met de tabel wijn, wijn_afkomst, wijn_category praat
 * Maakt gebruik van de WijnMapper om de Wijn objecten te maken
 */

@RegisterMapper(WijnMapper.class)
public interface WijnDAO {
  /**
   * Haalt alle wijnen op uit de database
   *
   * @return Collection<Wijn>
   */
  @SqlQuery("SELECT wijn_id, wijn_serie_id, wijn_naam, wijn_inkoopprijs, wijn_prijs, "
      + "wijn_type, wijn_jaartal, wijn_isactief, afkomst_naam,"
      + " category_naam FROM `wijn`, `wijn_afkomst`, `wijn_category`"
      + "WHERE wijn.wijn_afkomst = wijn_afkomst.afkomst_id AND wijn_afkomst.category_id = wijn_category.category_id")
  Collection<Wijn> retrieveAll();

  /**
   * Haalt alleen de actieve wijnen op uit de database
   *
   * @return Collection<Wijn>
   */
  @SqlQuery("SELECT wijn_id, wijn_serie_id, wijn_naam, wijn_inkoopprijs, wijn_prijs, "
      + "wijn_type, wijn_jaartal, wijn_isactief, afkomst_naam,"
      + " category_naam FROM `wijn`, `wijn_afkomst`, `wijn_category`"
      + "WHERE wijn.wijn_afkomst = wijn_afkomst.afkomst_id AND wijn_afkomst.category_id = wijn_category.category_id AND wijn_isactief = 1")
  Collection<Wijn> retreiveActive();

  /**
   * Haalt een enkele wijn opgevraagd aan de hand van het wijn serie id op uit de database
   *
   * @return Wijn
   */
  @SqlQuery("SELECT wijn_id, wijn_serie_id, wijn_naam, wijn_inkoopprijs, wijn_prijs, "
      + "wijn_type, wijn_jaartal, wijn_isactief, afkomst_naam,"
      + " category_naam FROM `wijn`, `wijn_afkomst`, `wijn_category`"
      + "WHERE wijn.wijn_afkomst = wijn_afkomst.afkomst_id AND "
      + "wijn_afkomst.category_id = wijn_category.category_id AND wijn_serie_id = :wijnID")
  Wijn retrieve(@Bind("wijnID") int wijnID);
}
