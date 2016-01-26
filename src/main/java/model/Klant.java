package model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.validation.constraints.Null;
import java.security.Principal;

/**
 * Edited by:
 * - Anton
 * - Roger
 */
public class Klant implements Principal {

  @NotEmpty
  @Email
  @JsonView(_View.View.Public.class)
  private String email;

  @JsonView(_View.View.Public.class)
  private String voornaam;

  @Nullable
  @JsonView(_View.View.Public.class)
  private String tussenvoegsel;

  @JsonView(_View.View.Public.class)
  private String achternaam;

  @JsonView(_View.View.Public.class)
  private String straatnaam;

  @JsonView(_View.View.Public.class)
  private int huisNummer;

  @Nullable
  @JsonView(_View.View.Public.class)
  private String huisNummerToevoeging;

  @Length(min = 4, max = 4)
  @JsonView(_View.View.Public.class)
  private int postcode;

  @Length(min = 2, max = 2)
  @JsonView(_View.View.Public.class)
  private String postcodeToevoeging;

  @JsonView(_View.View.Public.class)
  private String plaatsNaam;

  @Nullable
  @JsonView(_View.View.Public.class)
  private String telefoon;

  @Nullable
  @JsonView(_View.View.Public.class)
  private String gastLid;

  @Nullable
  @JsonView(_View.View.Public.class)
  private String notitie;

  @Nullable
  @JsonView(_View.View.Public.class)
  private int klantActief;

  @JsonView(_View.View.Public.class)
  private String dateString;

  @Length(min = 8)
  @JsonView(_View.View.Protected.class)
  private String password;

  @JsonView(_View.View.Public.class)
  private Boolean klantRechten;

  @JsonView(_View.View.Public.class)
  private Boolean lidRechten;

  @JsonView(_View.View.Public.class)
  private Boolean msRechten;

  @JsonView(_View.View.Public.class)
  private Boolean adminRechten;

  @JsonView(_View.View.Public.class)
  private Boolean accountActief;

  @JsonView(_View.View.Public.class)
  private Boolean wantsMail;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getVoornaam() {
    return voornaam;
  }

  public void setVoornaam(String voornaam) {
    this.voornaam = voornaam;
  }

  @Nullable
  public String getTussenvoegsel() {
    return tussenvoegsel;
  }

  public void setTussenvoegsel(@Nullable String tussenvoegsel) {
    this.tussenvoegsel = tussenvoegsel;
  }

  public String getAchternaam() {
    return achternaam;
  }

  public void setAchternaam(String achternaam) {
    this.achternaam = achternaam;
  }

  public String getStraatnaam() {
    return straatnaam;
  }

  public void setStraatnaam(String straatnaam) {
    this.straatnaam = straatnaam;
  }

  public int getHuisNummer() {
    return huisNummer;
  }

  public void setHuisNummer(int huisNummer) {
    this.huisNummer = huisNummer;
  }

  @Nullable
  public String getHuisNummerToevoeging() {
    return huisNummerToevoeging;
  }

  public void setHuisNummerToevoeging(@Nullable String huisNummerToevoeging) {
    this.huisNummerToevoeging = huisNummerToevoeging;
  }

  public int getPostcode() {
    return postcode;
  }

  public void setPostcode(int postcode) {
    this.postcode = postcode;
  }

  public String getPostcodeToevoeging() {
    return postcodeToevoeging;
  }

  public void setPostcodeToevoeging(String postcodeToevoeging) {
    this.postcodeToevoeging = postcodeToevoeging;
  }

  public String getPlaatsNaam() {
    return plaatsNaam;
  }

  public void setPlaatsNaam(String plaatsNaam) {
    this.plaatsNaam = plaatsNaam;
  }

  @Nullable
  public String getTelefoon() {
    return telefoon;
  }

  public void setTelefoon(@Nullable String telefoon) {
    this.telefoon = telefoon;
  }

  @Nullable
  public String getGastLid() {
    return gastLid;
  }

  public void setGastLid(@Nullable String gastLid) {
    this.gastLid = gastLid;
  }

  @Nullable
  public String getNotitie() {
    return notitie;
  }

  public void setNotitie(@Nullable String notitie) {
    this.notitie = notitie;
  }

  @Nullable
  public int getKlantActief() {
    return klantActief;
  }

  public void setKlantActief(@Nullable int klantActief) {
    this.klantActief = klantActief;
  }

  public String getDateString() {
    return dateString;
  }

  public void setDateString(String dateString) {
    this.dateString = dateString;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getKlantRechten() {
    return klantRechten;
  }

  public void setKlantRechten(Boolean klantRechten) {
    this.klantRechten = klantRechten;
  }

  public Boolean getLidRechten() {
    return lidRechten;
  }

  public void setLidRechten(Boolean lidRechten) {
    this.lidRechten = lidRechten;
  }

  public Boolean getMsRechten() {
    return msRechten;
  }

  public void setMsRechten(Boolean msRechten) {
    this.msRechten = msRechten;
  }

  public Boolean getAdminRechten() {
    return adminRechten;
  }

  public void setAdminRechten(Boolean adminRechten) {
    this.adminRechten = adminRechten;
  }

  public Boolean getAccountActief() {
    return accountActief;
  }

  public void setAccountActief(Boolean accountActief) {
    this.accountActief = accountActief;
  }

  public Boolean getWantsMail() {
    return wantsMail;
  }

  public void setWantsMail(Boolean wantsMail) {
    this.wantsMail = wantsMail;
  }

  public String toString() {
    return "Email: " +email
            + " Rechten: " + "GUEST" + getKlantRechten()
            + " LID" + getLidRechten() + " MS " + getMsRechten()
            + " ADMIN" + getAdminRechten();
  }

  public boolean hasRole(String role) {
    System.out.println("Klant.hasRole: role = " + role);
    switch (role) {
      case "GUEST":
        return klantRechten;
      case "LID":
        return lidRechten;
      case "M&S":
        return msRechten;
      case "ADMIN":
        return adminRechten;
      default:
        return false;
    }
  }

  @Override
  public String getName() {
    if (tussenvoegsel == null) {
      return voornaam + " " + achternaam;
    } else {
      return voornaam + " " + tussenvoegsel + " " + achternaam;
    }
  }
}
