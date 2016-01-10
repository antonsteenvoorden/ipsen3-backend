package model;

import View.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.security.auth.Subject;
import java.security.Principal;
import java.sql.Timestamp;

/**
 * Created by Anton on 10/01/2016.
 */
public class Order implements Principal {
  @JsonView(View.Public.class)
  private int orderID;

  @JsonView(View.Public.class)
  private String klantEmail;

  @JsonView(View.Public.class)
  private String factuurAdres;

  @JsonView(View.Public.class)
  private Timestamp orderDatum;

  @JsonView(View.Public.class)
  private boolean isActief;

  public int getOrderID() {
    return orderID;
  }

  public void setOrderID(int orderID) {
    this.orderID = orderID;
  }

  public String getKlantEmail() {
    return klantEmail;
  }

  public void setKlantEmail(String klantEmail) {
    this.klantEmail = klantEmail;
  }

  public String getFactuurAdres() {
    return factuurAdres;
  }

  public void setFactuurAdres(String factuurAdres) {
    this.factuurAdres = factuurAdres;
  }

  public Timestamp getOrderDatum() {
    return orderDatum;
  }

  public void setOrderDatum(Timestamp orderDatum) {
    this.orderDatum = orderDatum;
  }

  public boolean isActief() {
    return isActief;
  }

  public void setActief(boolean actief) {
    isActief = actief;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public boolean implies(Subject subject) {
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

}
