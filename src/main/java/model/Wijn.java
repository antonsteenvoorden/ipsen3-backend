package model;

import _View.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.security.Principal;


/**
 * Edited by:
 * - Anton
 * - Roger
 * <p/>
 * Model klasse voor de Wijn objecten
 * Wordt gebruikt door de WijnService/WijnDAO/WijnResource
 */
public class Wijn implements Principal {
  @JsonView(View.Public.class)
  private int wijnID;

  @JsonView(View.Public.class)
  private int wijnSerieID;

  @JsonView(View.Public.class)
  private String wijnNaam;

  @JsonView(View.Public.class)
  private double inkoopPrijs;

  @JsonView(View.Public.class)
  private double prijs;

  @JsonView(View.Public.class)
  private int wijnType;

  @JsonView(View.Public.class)
  private int wijnJaartal;

  @JsonView(View.Public.class)
  private int actief;

  @JsonView(View.Public.class)
  private String wijnAfkomst;

  @JsonView(View.Public.class)
  private String wijnCategory;

  public int getWijnID() {
    return wijnID;
  }

  public void setWijnID(int wijnID) {
    this.wijnID = wijnID;
  }

  public int getWijnSerieID() {
    return wijnSerieID;
  }

  public void setWijnSerieID(int wijnSerieID) {
    this.wijnSerieID = wijnSerieID;
  }

  public String getWijnNaam() {
    return wijnNaam;
  }

  public void setWijnNaam(String wijnNaam) {
    this.wijnNaam = wijnNaam;
  }

  public double getInkoopPrijs() {
    return inkoopPrijs;
  }

  public void setInkoopPrijs(double inkoopPrijs) {
    this.inkoopPrijs = inkoopPrijs;
  }

  public double getPrijs() {
    return prijs;
  }

  public void setPrijs(double prijs) {
    this.prijs = prijs;
  }

  public int getWijnType() {
    return wijnType;
  }

  public void setWijnType(int wijnType) {
    this.wijnType = wijnType;
  }

  public int getWijnJaartal() {
    return wijnJaartal;
  }

  public void setWijnJaartal(int wijnJaartal) {
    this.wijnJaartal = wijnJaartal;
  }

  public int getActief() {
    return actief;
  }

  public void setActief(int actief) {
    this.actief = actief;
  }

  public String getWijnAfkomst() {
    return wijnAfkomst;
  }

  public void setWijnAfkomst(String wijnAfkomst) {
    this.wijnAfkomst = wijnAfkomst;
  }

  public String getWijnCategory() {
    return wijnCategory;
  }

  public void setWijnCategory(String wijnCategory) {
    this.wijnCategory = wijnCategory;
  }

  @Override
  public String getName() {
    return wijnNaam;
  }
}
