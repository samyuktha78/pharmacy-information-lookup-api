package com.pharmacy.service;

import com.pharmacy.exception.DrugNotFoundException;
import com.pharmacy.model.Drug;
import com.pharmacy.repository.DrugRepository;
import com.pharmacy.validation.DrugValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DrugService {

  private final DrugRepository drugRepository;
  private final DrugValidator drugValidator;

  public DrugService(DrugRepository drugRepository, DrugValidator drugValidator) {
    this.drugRepository = drugRepository;
    this.drugValidator = drugValidator;
  }

  public List<Drug> getAllDrugs() {
    return drugRepository.findAll();
  }

  public Drug getDrugById(Long id) {
    return drugRepository.findById(id)
        .orElseThrow(() -> new DrugNotFoundException("Drug not found: " + id));
  }

  public Drug getDrugByNdcCode(String ndcCode) {
    return drugRepository.findByNdcCode(ndcCode)
        .orElseThrow(() -> new DrugNotFoundException("Drug not found for NDC code: " + ndcCode));
  }

  public List<Drug> searchByName(String name) {
    return drugRepository.findByNameContainingIgnoreCase(name);
  }

  public List<Drug> searchByGenericName(String genericName) {
    return drugRepository.findByGenericNameContainingIgnoreCase(genericName);
  }

  public List<Drug> getByStatus(Drug.DrugStatus status) {
    return drugRepository.findByStatus(status);
  }

  public List<Drug> getByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
    return drugRepository.findByPriceRange(minPrice, maxPrice);
  }

  public Drug createDrug(Drug drug) {
    List<String> errors = drugValidator.validate(drug);
    if (!errors.isEmpty()) {
      throw new IllegalArgumentException("Validation failed: " + String.join(", ", errors));
    }
    return drugRepository.save(drug);
  }

  public Drug updateDrug(Long id, Drug updated) {
    Drug existing = getDrugById(id);

    List<String> errors = drugValidator.validate(updated);
    if (!errors.isEmpty()) {
      throw new IllegalArgumentException("Validation failed: " + String.join(", ", errors));
    }

    existing.setNdcCode(updated.getNdcCode());
    existing.setName(updated.getName());
    existing.setGenericName(updated.getGenericName());
    existing.setManufacturer(updated.getManufacturer());
    existing.setDosageForm(updated.getDosageForm());
    existing.setStrength(updated.getStrength());
    existing.setPrice(updated.getPrice());
    existing.setStatus(updated.getStatus());
    existing.setRequiresPrescription(updated.isRequiresPrescription());

    return drugRepository.save(existing);
  }

  public void deleteDrug(Long id) {
    Drug existing = getDrugById(id);
    drugRepository.delete(existing);
  }
}
