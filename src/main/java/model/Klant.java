//package model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonView;
//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.constraints.NotEmpty;
//import petersinspiratiepakket.actorius.ViewKaas.View;
//
//import java.security.Principal;
//
///**
// * Created by Anton on 07/01/2016.
// */
//public class Klant implements Principal {
//
//  @NotEmpty
//  @Email
//  @JsonView(ViewKaas.View.Public.class)
//  private String email;
//
//  @JsonView(ViewKaas.View.Public.class)
//  private String voornaam;
//
//  @JsonView(ViewKaas.View.Public.class)
//  private String tussenvoegsel;
//
//  @JsonView(ViewKaas.View.Public.class)
//  private String achternaam;
//
//  @JsonView(ViewKaas.View.Public.class)
//  private String straatnaam;
//
//  @JsonView(ViewKaas.View.Public.class)
//  private int huisNummer;
//
//  @JsonView(ViewKaas.View.Public.class)
//  private String huisNummerToevoeging;
//
//  @Length(min = 4, max = 4)
//  @JsonView(ViewKaas.View.Public.class)
//  private int postcode;
//
//  @Length(min = 2, max = 2)
//  @JsonView(ViewKaas.View.Public.class)
//  private String postcodeToevoeging;
//
//  @JsonView(ViewKaas.View.Public.class)
//  private String plaatsNaam;
//
//  @JsonView(ViewKaas.View.Public.class)
//  private String telefoon;
//
//  @NotEmpty
//  @JsonView(ViewKaas.View.Public.class)
//  private String gastLid;
//
//  @JsonView(ViewKaas.View.Public.class)
//  private String notitie;
//
//  @JsonView(ViewKaas.View.Public.class)
//  private int isActief;
//
//  @JsonView(ViewKaas.View.Public.class)
//  private String dateString;
//
//  @NotEmpty
//  @Length(min = 8)
//  @JsonView(ViewKaas.View.Protected.class)
//  private String password;
//
//  @JsonView(ViewKaas.View.Private.class)
//  private String[] roles;
//
//  public Klant() {
//
//  }
//
//
//  @Override
//  public boolean equals(Object another) {
//    return false;
//  }
//
//  @Override
//  public String toString() {
//    return null;
//  }
//
//  @Override
//  public int hashCode() {
//    return 0;
//  }
//
//  @Override
//  @JsonIgnore
//  public String getName() {
//    return achternaam;
//  }
//
//  public String getEmail() {
//    return email;
//  }
//
//  public void setEmail(String email) {
//    this.email = email;
//  }
//  public String getVoornaam() {
//    return tussenvoegsel;
//  }
//
//  public void setVoornaam(String voornaam) {
//    this.voornaam = voornaam;
//  }
//
//  public String getTussenvoegsel() {
//    return tussenvoegsel;
//  }
//
//  public void setTussenvoegsel(String tussenvoegsel) {
//    this.tussenvoegsel = tussenvoegsel;
//  }
//
//  public int getHuisNummer() {
//    return huisNummer;
//  }
//
//  public void setHuisNummer(int huisNummer) {
//    this.huisNummer = huisNummer;
//  }
//
//  public String getHuisNummerToevoeging() {
//    return huisNummerToevoeging;
//  }
//
//  public void setHuisNummerToevoeging(String huisNummerToevoeging) {
//    this.huisNummerToevoeging = huisNummerToevoeging;
//  }
//
//  public int getPostcode() {
//    return postcode;
//  }
//
//  public void setPostcode(int postcode) {
//    this.postcode = postcode;
//  }
//
//  public String getPostcodeToevoeging() {
//    return postcodeToevoeging;
//  }
//
//  public void setPostcodeToevoeging(String postcodeToevoeging) {
//    this.postcodeToevoeging = postcodeToevoeging;
//  }
//
//  public String getPlaatsNaam() {
//    return plaatsNaam;
//  }
//
//  public void setPlaatsNaam(String plaatsNaam) {
//    this.plaatsNaam = plaatsNaam;
//  }
//
//  public String getTelefoon() {
//    return telefoon;
//  }
//
//  public void setTelefoon(String telefoon) {
//    this.telefoon = telefoon;
//  }
//
//  public String getGastLid() {
//    return gastLid;
//  }
//
//  public void setGastLid(String gastLid) {
//    this.gastLid = gastLid;
//  }
//
//  public String getNotitie() {
//    return notitie;
//  }
//
//  public void setNotitie(String notitie) {
//    this.notitie = notitie;
//  }
//
//  public int getIsActief() {
//    return isActief;
//  }
//
//  public void setIsActief(int isActief) {
//    this.isActief = isActief;
//  }
//
//  public String getDateString() {
//    return dateString;
//  }
//
//  public void setDateString(String dateString) {
//    this.dateString = dateString;
//  }
//
//  public String getPassword() {
//    return password;
//  }
//
//  public void setPassword(String password) {
//    this.password = password;
//  }
//
//  public void setRoles(String[] roles)
//  {
//    this.roles = roles;
//  }
//
//  public boolean hasRole(String roleName)
//  {
//    if (roles != null)
//    {
//      for(String role : roles)
//      {
//        if(roleName.equals(role))
//        {
//          return true;
//        }
//      }
//    }
//
//    return false;
//  }
//
//  public boolean equals(Klant klant) {
//    return email.equals(klant.getEmail());
//  }
//
//  public String getAchternaam() {
//    return achternaam;
//  }
//
//  public void setAchternaam(String achternaam) {
//    this.achternaam = achternaam;
//  }
//
//  public String getStraatnaam() {
//    return straatnaam;
//  }
//
//  public void setStraatnaam(String straatnaam) {
//    this.straatnaam = straatnaam;
//  }
//}
