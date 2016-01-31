package model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created by roger on 31-1-2016.
 */
public class KpiTotaalBedrag {
  @JsonView(_View.View.Public.class)
  private int totaalBedrag;

  public int getTotaalBedrag() {
    return totaalBedrag;
  }

  public void setTotaalBedrag(int totaalBedrag) {
    this.totaalBedrag = totaalBedrag;
  }
}
