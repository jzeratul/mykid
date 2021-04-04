package org.jzeratul.mykid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.OffsetDateTime;

@Entity
public class DbKidStats {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "DATETIME")
  private OffsetDateTime datetime;

  @Column(name = "ACTIVITIES")
  private String activities;
  @Column(name = "TEMP")
  private Double temperature;
  @Column(name = "FFL_DUR")
  private Double feedFromLeftDuration;
  @Column(name = "FFR_DUR")
  private Double feedFromRightDuration;
  @Column(name = "PFL_QTY")
  private Double pumpFromLeftQuantity;
  @Column(name = "PFR_QTY")
  private Double pumpFromRightQuantity;
  @Column(name = "WEIGHT")
  private Double weight;
  @Column(name = "EXTRA_M_QTY")
  private Double extraBottleMotherMilkQuantity;
  @Column(name = "EXTRA_F_QTY")
  private Double extraBottleFormulaeMilkQuantity;
  @Column(name = "CREATED_AT")
  private OffsetDateTime createdAt;

  public Long id() {
    return id;
  }

  public DbKidStats id(Long id) {
    this.id = id;
    return this;
  }

  public OffsetDateTime datetime() {
    return datetime;
  }

  public DbKidStats datetime(OffsetDateTime datetime) {
    this.datetime = datetime;
    return this;
  }

  public String activities() {
    return activities;
  }

  public DbKidStats activities(String activities) {
    this.activities = activities;
    return this;
  }

  public Double temperature() {
    return temperature;
  }

  public DbKidStats temperature(Double temperature) {
    this.temperature = temperature;
    return this;
  }

  public Double feedFromLeftDuration() {
    return feedFromLeftDuration;
  }

  public DbKidStats feedFromLeftDuration(Double feedFromLeftDuration) {
    this.feedFromLeftDuration = feedFromLeftDuration;
    return this;
  }

  public Double feedFromRightDuration() {
    return feedFromRightDuration;
  }

  public DbKidStats feedFromRightDuration(Double feedFromRightDuration) {
    this.feedFromRightDuration = feedFromRightDuration;
    return this;
  }

  public Double pumpFromLeftQuantity() {
    return pumpFromLeftQuantity;
  }

  public DbKidStats pumpFromLeftQuantity(Double pumpFromLeftQuantity) {
    this.pumpFromLeftQuantity = pumpFromLeftQuantity;
    return this;
  }

  public Double pumpFromRightQuantity() {
    return pumpFromRightQuantity;
  }

  public DbKidStats pumpFromRightQuantity(Double pumpFromRightQuantity) {
    this.pumpFromRightQuantity = pumpFromRightQuantity;
    return this;
  }

  public Double weight() {
    return weight;
  }

  public DbKidStats weight(Double weight) {
    this.weight = weight;
    return this;
  }

  public Double extraBottleMotherMilkQuantity() {
    return extraBottleMotherMilkQuantity;
  }

  public DbKidStats extraBottleMotherMilkQuantity(Double extraBottleMotherMilkQuantity) {
    this.extraBottleMotherMilkQuantity = extraBottleMotherMilkQuantity;
    return this;
  }

  public Double extraBottleFormulaeMilkQuantity() {
    return extraBottleFormulaeMilkQuantity;
  }

  public DbKidStats extraBottleFormulaeMilkQuantity(Double extraBottleFormulaeMilkQuantity) {
    this.extraBottleFormulaeMilkQuantity = extraBottleFormulaeMilkQuantity;
    return this;
  }

  public OffsetDateTime createdAt() {
    return createdAt;
  }

  public DbKidStats createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }
}
