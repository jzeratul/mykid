package org.jzeratul.mykid;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;

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

  public DbKidStats() {
  }

  public Long getId() {
    return id;
  }

  public DbKidStats setId(Long id) {
    this.id = id;
    return this;
  }

  public OffsetDateTime getDatetime() {
    return datetime;
  }

  public DbKidStats setDatetime(OffsetDateTime datetime) {
    this.datetime = datetime;
    return this;
  }

  public String getActivities() {
    return activities;
  }

  public DbKidStats setActivities(String activities) {
    this.activities = activities;
    return this;
  }

  public Double getTemperature() {
    return temperature;
  }

  public DbKidStats setTemperature(Double temperature) {
    this.temperature = temperature;
    return this;
  }

  public Double getFeedFromLeftDuration() {
    return feedFromLeftDuration;
  }

  public DbKidStats setFeedFromLeftDuration(Double feedFromLeftDuration) {
    this.feedFromLeftDuration = feedFromLeftDuration;
    return this;
  }

  public Double getFeedFromRightDuration() {
    return feedFromRightDuration;
  }

  public DbKidStats setFeedFromRightDuration(Double feedFromRightDuration) {
    this.feedFromRightDuration = feedFromRightDuration;
    return this;
  }

  public Double getPumpFromLeftQuantity() {
    return pumpFromLeftQuantity;
  }

  public DbKidStats setPumpFromLeftQuantity(Double pumpFromLeftQuantity) {
    this.pumpFromLeftQuantity = pumpFromLeftQuantity;
    return this;
  }

  public Double getPumpFromRightQuantity() {
    return pumpFromRightQuantity;
  }

  public DbKidStats setPumpFromRightQuantity(Double pumpFromRightQuantity) {
    this.pumpFromRightQuantity = pumpFromRightQuantity;
    return this;
  }

  public Double getWeight() {
    return weight;
  }

  public DbKidStats setWeight(Double weight) {
    this.weight = weight;
    return this;
  }

  public Double getExtraBottleMotherMilkQuantity() {
    return extraBottleMotherMilkQuantity;
  }

  public DbKidStats setExtraBottleMotherMilkQuantity(Double extraBottleMotherMilkQuantity) {
    this.extraBottleMotherMilkQuantity = extraBottleMotherMilkQuantity;
    return this;
  }

  public Double getExtraBottleFormulaeMilkQuantity() {
    return extraBottleFormulaeMilkQuantity;
  }

  public DbKidStats setExtraBottleFormulaeMilkQuantity(Double extraBottleFormulaeMilkQuantity) {
    this.extraBottleFormulaeMilkQuantity = extraBottleFormulaeMilkQuantity;
    return this;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public DbKidStats setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

}
