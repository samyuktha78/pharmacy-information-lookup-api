package com.pharmacy.validation;

import com.pharmacy.model.Drug;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DrugValidator {

  public List<String> validate(Drug drug) {
    List<String> errors = new ArrayList<>();

    if (drug.getNdcCode() == null || drug.getNdcCode().isBlank()) {
      errors.add("NDC code is required");
    }
    if (drug.getName() == null || drug.getName().isBlank()) {
      errors.add("Drug name is required");
    }
    if (drug.getPrice() == null || drug.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
      errors.add("Price must be greater than zero");
    }
    if (drug.getStatus() == null) {
      errors.add("Drug status is required");
    }

    return errors;
  }
}
