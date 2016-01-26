package model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Anton on 13/01/2016.
 */
public class Mail {

  @NotEmpty
  private String onderwerp;

  @NotEmpty
  private String tekst;

  public String getOnderwerp() {
    return onderwerp;
  }

  public void setOnderwerp(String onderwerp) {
    this.onderwerp = onderwerp;
  }

  public String getTekst() {
    return tekst;
  }

  public void setTekst(String tekst) {
    this.tekst = tekst;
  }
}
