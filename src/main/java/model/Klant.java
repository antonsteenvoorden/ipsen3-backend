package model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import petersinspiratiepakket.actorius.View;

import java.security.Principal;
import java.sql.Timestamp;

/**
 * Created by Anton on 07/01/2016.
 */
public class Klant extends iipsen2.model.Klant implements Principal {

  @NotEmpty
  @Email
  @JsonView(View.Public.class)
  private String email;

  @NotEmpty
  @Length(min = 8)
  @JsonView(View.Protected.class)
  private String password;

  @JsonView(View.Private.class)
  private String[] roles;

  public Klant(String email, String voornaam, String tussenvoegsel, String achternaam, String straatnaam, int huisNummer, String huisNummerToevoeging, int postcode, String postcodeToevoeging, String plaatsNaam, String telefoon, String gastLid, String notitie, int isActief, Timestamp creationDate) {
    super(email, voornaam, tussenvoegsel, achternaam, straatnaam, huisNummer, huisNummerToevoeging, postcode, postcodeToevoeging, plaatsNaam, telefoon, gastLid, notitie, isActief, creationDate);
  }


  @Override
  public boolean equals(Object another) {
    return false;
  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public String getName() {
    return null;
  }
}
