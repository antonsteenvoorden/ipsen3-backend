package service;

import dao.KpiDAO;
import dao.KpiTotaalBedragDAO;
import model.Klant;
import model.Kpi;
import model.KpiTotaalBedrag;

import javax.ws.rs.ForbiddenException;
import java.util.Set;

/**
 * Edited by:
 * -Jordan
 * -Sidney
 * -Dennis
 * -Roger
 */
public class KpiService extends BaseService<Kpi> {
  private KpiDAO kpiDAO;
  private KpiTotaalBedragDAO kpiTotaalBedragDAO;

  public KpiService(KpiDAO kpiDAO, KpiTotaalBedragDAO kpiTotaalBedragDAO) {
    this.kpiDAO = kpiDAO;
    this.kpiTotaalBedragDAO = kpiTotaalBedragDAO;
  }

  /**
   * Haalt eerst een Kpi op zonder totaalbedrag.
   * Haalt de totaalbedragset op.
   * Telt deze bij elkaar op en het resultaat word gezet in het Kpi object.
   * @param authorisation
   * @return
   */
  public Kpi getKpi(Klant authorisation) {
    if (authorisation.hasRole("ADMIN") || authorisation.hasRole("MS")) {

      Kpi kpi = kpiDAO.get();
      //kpi.setKpiTotaalBedrag(kpiTotaalBedragDAO.get());
      Set<KpiTotaalBedrag> totaalBedragSet = kpiTotaalBedragDAO.get();
      int totaal = 0;
      for (KpiTotaalBedrag kpiTotaalBedrag : totaalBedragSet) {
        totaal += kpiTotaalBedrag.getTotaalBedrag();
        System.out.println("Value is now: " + totaal);
      }
      kpi.setKpiTotaalBedrag(totaal);
      System.out.println("KpiService.getKpi: " + kpi.toString());
      return kpi;
    } else {
      throw new ForbiddenException();
    }
  }
}
