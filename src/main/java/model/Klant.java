package model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

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

    @JsonView(_View.View.Public.class)
    private String tussenvoegsel;

    @JsonView(_View.View.Public.class)
    private String achternaam;

    @JsonView(_View.View.Public.class)
    private String straatnaam;

    @JsonView(_View.View.Public.class)
    private int huisNummer;

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

    @JsonView(_View.View.Public.class)
    private String telefoon;

    @NotEmpty
    @JsonView(_View.View.Public.class)
    private String gastLid;

    @JsonView(_View.View.Public.class)
    private String notitie;

    @JsonView(_View.View.Public.class)
    private int isActief;

    @JsonView(_View.View.Public.class)
    private String dateString;

    @NotEmpty
    @Length(min = 8)
    @JsonView(_View.View.Protected.class)
    private String password;

    @JsonView(_View.View.Private.class)
    private String[] roles;
    //TODO: convert to enum
    
    @JsonView(_View.View.Public.class)
    private boolean inMailingList;

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

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
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

    public String getHuisNummerToevoeging() {
        return huisNummerToevoeging;
    }

    public void setHuisNummerToevoeging(String huisNummerToevoeging) {
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

    public String getTelefoon() {
        return telefoon;
    }

    public void setTelefoon(String telefoon) {
        this.telefoon = telefoon;
    }

    public String getGastLid() {
        return gastLid;
    }

    public void setGastLid(String gastLid) {
        this.gastLid = gastLid;
    }

    public String getNotitie() {
        return notitie;
    }

    public void setNotitie(String notitie) {
        this.notitie = notitie;
    }

    public int getIsActief() {
        return isActief;
    }

    public void setIsActief(int isActief) {
        this.isActief = isActief;
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

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public boolean hasRole(String roleName) {
        if (roles != null) {
            for (String role : roles) {
                if (roleName.equals(role)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override public String getName() {
        if (tussenvoegsel == null) {
            return voornaam + " " + achternaam;
        } else {
            return voornaam + " " + tussenvoegsel + " " + achternaam;
        }
    }
}
