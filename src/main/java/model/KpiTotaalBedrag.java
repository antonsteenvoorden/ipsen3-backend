package model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Edited by:
 * -Jordan
 * -Sidney
 * -Dennis
 * -Roger
 * <p/>
 * Represententeerd een totaalbedrag van een order.
 * Heeff geen resource, word alleen gebruikt om later mee te rekenen.
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
