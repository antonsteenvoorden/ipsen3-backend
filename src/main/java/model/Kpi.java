package model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Edited by:
 * -Jordan
 * -Sidney
 * -Dennis
 * -Roger
 * <p/>
 * Represententeerd een kpi welke het aantal klanten, aantal orders en totaalbedrag aan orders bevat.
 */
public class Kpi {
  @JsonView(_View.View.Public.class)
  private Integer aantalKlanten;

  @JsonView(_View.View.Public.class)
  private Integer aantalOrders;

  @JsonView(_View.View.Public.class)
  private Integer kpiTotaalBedrag;

  public int getAantalKlanten() {
    return aantalKlanten;
  }

  public void setAantalKlanten(int aantalKlanten) {
    this.aantalKlanten = aantalKlanten;
  }

  public int getAantalOrders() {
    return aantalOrders;
  }

  public void setAantalOrders(int aantalOrders) {
    this.aantalOrders = aantalOrders;
  }

  public int getKpiTotaalBedrag() {
    return kpiTotaalBedrag;
  }

  public void setKpiTotaalBedrag(int kpiTotaalBedrag) {
    this.kpiTotaalBedrag = kpiTotaalBedrag;
  }

  @Override
  public String toString() {
    if (aantalKlanten == null || aantalOrders == null && kpiTotaalBedrag == null) {
      return "Some attribute(s) empty, calling super.tostring: " + super.toString();
    }
    return "Aantal klanten: " + aantalKlanten + ", aantal Orders: " + aantalOrders + ", Totaalbedrag: " + kpiTotaalBedrag;
  }
}
