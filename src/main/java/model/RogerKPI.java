package model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created by roger on 30-1-2016.
 */
public class RogerKPI {
  @JsonView(_View.View.Public.class)
  private int aantalKlanten;

  public int getAantalKlanten() {
    return aantalKlanten;
  }

  public void setAantalKlanten(int aantalKlanten) {
    this.aantalKlanten = aantalKlanten;
  }
}
