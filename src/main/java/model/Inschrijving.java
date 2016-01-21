package model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Anton on 20/01/2016.
 */
public class Inschrijving {
  @NotEmpty
  private int actie_id;

  @NotEmpty
  private String klant_email;

  @NotEmpty
  private String actieInschrijvingTimestamp;

  public int getActie_id() {
    return actie_id;
  }

  public void setActie_id(int actie_id) {
    this.actie_id = actie_id;
  }

  public String getKlant_email() {
    return klant_email;
  }

  public void setKlant_email(String klant_email) {
    this.klant_email = klant_email;
  }

  public String getActieInschrijvingTimestamp() {
    return actieInschrijvingTimestamp;
  }

  public void setActieInschrijvingTimestamp(String actieInschrijvingTimestamp) {
    this.actieInschrijvingTimestamp = actieInschrijvingTimestamp;
  }
}
