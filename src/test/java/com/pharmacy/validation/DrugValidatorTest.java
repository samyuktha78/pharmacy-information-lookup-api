package com.pharmacy.validation;

import com.pharmacy.model.Drug;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DrugValidatorTest {

  private final DrugValidator validator = new DrugValidator();

  private Drug validDrug() {
    return new Drug("NDC001", "Amoxicillin", "Amoxicillin", "Pfizer",
        "capsule", "500mg", new BigDecimal("15.00"), Drug.DrugStatus.AVAILABLE, true);
  }

  @Test
  void validate_validDrug_returnsNoErrors() {
    List<String> errors = validator.validate(validDrug());
    assertTrue(errors.isEmpty());
  }

  @Test
  void validate_missingNdcCode_returnsError() {
    Drug drug = validDrug();
    drug.setNdcCode("");
    List<String> errors = validator.validate(drug);
    assertTrue(errors.contains("NDC code is required"));
  }

  @Test
  void validate_missingName_returnsError() {
    Drug drug = validDrug();
    drug.setName(null);
    List<String> errors = validator.validate(drug);
    assertTrue(errors.contains("Drug name is required"));
  }

  @Test
  void validate_negativePrice_returnsError() {
    Drug drug = validDrug();
    drug.setPrice(new BigDecimal("-5.00"));
    List<String> errors = validator.validate(drug);
    assertTrue(errors.contains("Price must be greater than zero"));
  }

  @Test
  void validate_zeroPrice_returnsError() {
    Drug drug = validDrug();
    drug.setPrice(BigDecimal.ZERO);
    List<String> errors = validator.validate(drug);
    assertTrue(errors.contains("Price must be greater than zero"));
  }

  @Test
  void validate_missingStatus_returnsError() {
    Drug drug = validDrug();
    drug.setStatus(null);
    List<String> errors = validator.validate(drug);
    assertTrue(errors.contains("Drug status is required"));
  }

  @Test
  void validate_multipleErrors_returnsAll() {
    Drug drug = new Drug("", "", null, null, null, null, null, null, false);
    List<String> errors = validator.validate(drug);
    assertEquals(4, errors.size());
  }
}
