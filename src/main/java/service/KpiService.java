package service;

import dao.KpiDAO;
import dao.KpiTotaalBedragDAO;
import model.Kpi;
import model.KpiTotaalBedrag;

import java.util.Set;

/**
 * Created by roger on 31-1-2016.
 */
public class KpiService {
  private KpiDAO kpiDAO;
  private KpiTotaalBedragDAO kpiTotaalBedragDAO;

  public KpiService(KpiDAO kpiDAO, KpiTotaalBedragDAO kpiTotaalBedragDAO) {
    this.kpiDAO = kpiDAO;
    this.kpiTotaalBedragDAO = kpiTotaalBedragDAO;
  }

  public Kpi getKpi() {
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
  }
}
