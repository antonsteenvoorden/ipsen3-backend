package model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.annotation.Nullable;

/**
 * Edited by:
 * - Roger
 */
public class OrderRegel {
  @JsonView(_View.View.Public.class)
  private int orderRegelID;

  @JsonView(_View.View.Public.class)
  private int wijnID;

  @JsonView(_View.View.Public.class)
  private String wijnNaam;

  @JsonView(_View.View.Public.class)
  private int wijnJaartal;

  @JsonView(_View.View.Public.class)
  private int aantal;

  @JsonView(_View.View.Public.class)
  private int orderID;

  @JsonView(_View.View.Public.class)
  private int isActief;

  @JsonView(_View.View.Public.class)
  private double wijnPrijs;

  @Nullable
  private Wijn wijn;

  public int getOrderRegelID() {
    return orderRegelID;
  }

  public void setOrderRegelID(int orderRegelID) {
    this.orderRegelID = orderRegelID;
  }

  public int getWijnID() {
    return wijnID;
  }

  public void setWijnID(int wijnID) {
    this.wijnID = wijnID;
  }

  public String getWijnNaam() {
    return wijnNaam;
  }

  public void setWijnNaam(String wijnNaam) {
    this.wijnNaam = wijnNaam;
  }

  public int getWijnJaartal() {
    return wijnJaartal;
  }

  public void setWijnJaartal(int wijnJaartal) {
    this.wijnJaartal = wijnJaartal;
  }

  public int getAantal() {
    return aantal;
  }

  public void setAantal(int aantal) {
    this.aantal = aantal;
  }

  public int getOrderID() {
    return orderID;
  }

  public void setOrderID(int orderID) {
    this.orderID = orderID;
  }

  public int getIsActief() {
    return isActief;
  }

  public void setIsActief(int isActief) {
    this.isActief = isActief;
  }

  public double getWijnPrijs() {
    return wijnPrijs;
  }

  public void setWijnPrijs(double wijnPrijs) {
    this.wijnPrijs = wijnPrijs;
  }

  @Nullable
  public Wijn getWijn() {
    return wijn;
  }

  public void setWijn(@Nullable Wijn wijn) {
    this.wijn = wijn;
  }
}
