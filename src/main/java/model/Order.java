package model;

import _View.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.annotation.Nullable;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Edited by:
 * - Anton
 * - Roger
 * Model klasse die gebruikt word als representatie van een order in de applicatie.
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
  private int isActief = -1;

  @JsonView(View.Public.class)
  @Nullable
  private Set<OrderRegel> orderRegelSet;

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

  public int getIsActief() {
    return isActief;
  }

  public void setIsActief(int isActief) {
    this.isActief = isActief;
  }

  @Nullable public Set<OrderRegel> getOrderRegelSet() {
    return orderRegelSet;
  }

  public void setOrderRegelSet(@Nullable Set<OrderRegel> orderRegelSet) {
    this.orderRegelSet = orderRegelSet;
  }

  @Override
  public String getName() {
    return null;
  }

}
