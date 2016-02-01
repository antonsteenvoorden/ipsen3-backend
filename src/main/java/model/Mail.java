package model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Edited by:
 * - Anton
 * <p/>
 * Model klasse die de mail bevat. Wordt gebruikt door de nieuwsbrief en de wachtwoordvergeten
 * functies.
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
