package com.pharmacy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "drugs")
public class Drug {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, unique = true)
  private String ndcCode; // National Drug Code

  @NotBlank
  private String name;

  private String genericName;

  private String manufacturer;

  private String dosageForm; // tablet, capsule, liquid, etc.

  private String strength; // e.g. 500mg

  @NotNull
  @Positive
  private BigDecimal price;

  @Enumerated(EnumType.STRING)
  private DrugStatus status;

  private boolean requiresPrescription;

  public enum DrugStatus {
    AVAILABLE, OUT_OF_STOCK, DISCONTINUED
  }

  public Drug() {
  }

  public Drug(String ndcCode, String name, String genericName, String manufacturer,
              String dosageForm, String strength, BigDecimal price, DrugStatus status,
              boolean requiresPrescription) {
    this.ndcCode = ndcCode;
    this.name = name;
    this.genericName = genericName;
    this.manufacturer = manufacturer;
    this.dosageForm = dosageForm;
    this.strength = strength;
    this.price = price;
    this.status = status;
    this.requiresPrescription = requiresPrescription;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNdcCode() {
    return ndcCode;
  }

  public void setNdcCode(String ndcCode) {
    this.ndcCode = ndcCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGenericName() {
    return genericName;
  }

  public void setGenericName(String genericName) {
    this.genericName = genericName;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getDosageForm() {
    return dosageForm;
  }

  public void setDosageForm(String dosageForm) {
    this.dosageForm = dosageForm;
  }

  public String getStrength() {
    return strength;
  }

  public void setStrength(String strength) {
    this.strength = strength;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public DrugStatus getStatus() {
    return status;
  }

  public void setStatus(DrugStatus status) {
    this.status = status;
  }

  public boolean isRequiresPrescription() {
    return requiresPrescription;
  }

  public void setRequiresPrescription(boolean requiresPrescription) {
    this.requiresPrescription = requiresPrescription;
  }
}
