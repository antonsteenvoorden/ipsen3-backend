package model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Edited by:
 * - Anton
 * <p/>
 * Model klasse die een actie represententeerd.
 * Word gebruikt om de online acties van de Lion's te representeren
 */
public class Actie {
  @NotEmpty
  @Email
  @JsonView(_View.View.Public.class)
  private int id;

  @NotEmpty
  @JsonView(_View.View.Public.class)
  private boolean actieActief;

  @NotEmpty
  @JsonView(_View.View.Public.class)
  private String startTimestamp;

  @NotEmpty
  @JsonView(_View.View.Public.class)
  private String eindTimestamp;

  @NotEmpty
  @JsonView(_View.View.Public.class)
  private String referentieNaam;

  @JsonView(_View.View.Public.class)
  private String beschrijving;

  public String getBeschrijving() {
    return beschrijving;
  }

  public void setBeschrijving(String beschrijving) {
    this.beschrijving = beschrijving;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isActieActief() {
    return actieActief;
  }

  public void setActieActief(boolean actieActief) {
    this.actieActief = actieActief;
  }

  public String getStartTimestamp() {
    return startTimestamp;
  }

  public void setStartTimestamp(String startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  public String getEindTimestamp() {
    return eindTimestamp;
  }

  public void setEindTimestamp(String eindTimestamp) {
    this.eindTimestamp = eindTimestamp;
  }

  public String getReferentieNaam() {
    return referentieNaam;
  }

  public void setReferentieNaam(String referentieNaam) {
    this.referentieNaam = referentieNaam;
  }
}
